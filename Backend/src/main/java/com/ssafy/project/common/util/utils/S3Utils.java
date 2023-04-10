package com.ssafy.project.common.util.utils;

import com.ssafy.project.common.provider.S3Provider;
import com.ssafy.project.common.security.exception.CommonApiException;
import com.ssafy.project.common.util.constant.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jcodec.api.FrameGrab;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class S3Utils {

    private final String IMAGE_DIR_PATH = "img/";
    private final String GRAPH_DIR_PATH = "graph/";
    private final String VIDEO_DIR_PATH = "video/";
    private final String IMAGE_PREFIX = "s_";
    private final String GRAPH_PREFIX = "g_";
    private final String PNG_FORMAT = ".PNG";
    private final String MP4_FORMAT = ".mp4";

    @Value("${spring.servlet.multipart.location}")
    private String EC2_DIR_PATH;

    private final S3Provider s3Provider;

    @Transactional
    public List<String> upload(MultipartFile multipartVideoFile, MultipartFile multipartGraphFile) {

        try {

            String baseUri = makeUri(multipartVideoFile);
            String videoUri = getVideoUri(baseUri);
            String thumbnailUri = getThumbnailUri(baseUri);
            String graphUri = getGraphUri(baseUri);

            log.info(EC2_DIR_PATH + thumbnailUri);
            log.info(EC2_DIR_PATH + videoUri);
            // 섬네일 임시파일 생성
            File thumbnailFile = new File(EC2_DIR_PATH + thumbnailUri);
            thumbnailFile.createNewFile();

            // 비디오 임시파일 생성
            File videoFile = new File(EC2_DIR_PATH + videoUri);

            // 비디오 임시파일에 복사
            // 용량이 큰 경우 메모리보다 디스크를 사용해야하며, temp에있는 비디오 바로 사용시 보안/권한/동시성 문제를 해결해야 함
            multipartVideoFile.transferTo(videoFile);

            int frameNumber = 0;
            Picture picture = FrameGrab.getFrameFromFile(videoFile, frameNumber);
            BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);
            ImageIO.write(bufferedImage, PNG_FORMAT, thumbnailFile);

            List<String> Uris = new ArrayList<>();

            Uris.add(s3Provider.uploadFile(videoFile, videoUri));
            Uris.add(s3Provider.uploadFile(thumbnailFile, thumbnailUri));
            Uris.add(s3Provider.uploadMultipartFile(multipartGraphFile, graphUri));

            // boolean값 반환, false일 경우 파일이 남아있을 위험이 있으므로 처리해야 함
            videoFile.delete();
            thumbnailFile.delete();

            return Uris;

        }
        catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error(e.getLocalizedMessage());
            throw new CommonApiException(CommonErrorCode.FILE_NOT_VALID); }
    }

    public String makeUri(MultipartFile file) {
        StringBuilder sb = new StringBuilder();

        String fileName = String.valueOf(file.getOriginalFilename());
        int lastIndex = fileName.lastIndexOf('.');

        sb.append(UUID.randomUUID())
          .append("_")
          .append(lastIndex == -1? fileName : fileName.substring(0, lastIndex)
                  .replaceAll("[~!@#$%^&*()_+ ]", "_"));

        return sb.toString();
    }

    public String getVideoUri(String uri) { return VIDEO_DIR_PATH + uri + MP4_FORMAT; };

    public String getThumbnailUri(String uri) { return IMAGE_DIR_PATH + IMAGE_PREFIX + uri + PNG_FORMAT; };

    public String getGraphUri(String uri) { return GRAPH_DIR_PATH + GRAPH_PREFIX + uri + PNG_FORMAT; };

}
