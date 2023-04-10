package com.ssafy.project.common.util.init;


import com.ssafy.project.common.db.entity.base.EmotionEnum;
import com.ssafy.project.common.db.entity.base.GenreEnum;
import com.ssafy.project.common.db.entity.common.Script;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    public final InitService initService;

    @PostConstruct
    public void init(){
        initService.scriptInit();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{

        public final EntityManager em;

        public void scriptInit() {
            Script script1 =  createScript(EmotionEnum.기쁨, GenreEnum.드라마, "더 글로리", "김은숙", "문동은", "화이팅 박연진! 브라보! 멋지다, 연진아!", 0L);
            em.persist(script1);

            Script script3 = createScript(EmotionEnum.두려움, GenreEnum.드라마, "펜트 하우스", "김순옥", "천서진", "기다릴 시간이 없었어. 내 예감이 맞았어. 당신 와이프, 민설아와 관계있어. 당신 와이프가 오늘 누굴 만났는지 알아? 민설아를 키운 보육원 원장을 만났어. 그것도 구치소에서. 민형식 원장. 알아보니 조상원 의원의 신복이었대. 보육원 아이들 골수 팔이까지 한 흉악범을 당신 와이프가 왜 면회까지 하겠어. 틀림없이 민설아와 관련이 있는거야. 민설아가 죽는 걸 목격했든 아님 민설아와 원래 아는 사람이든. 민설아, 고아잖아. 혹시 당신 와이프가 민설아 친부모를 알고 있는 거 아니야? 1%의 의심점이라도 있다면 확인해봐야 될 거 아니야. 당신 와이프 뭔가 있어. 내 말 명심해. 절대 그냥 넘어갈 일 아니야.", 0L);
            em.persist(script3);

            Script script4 = createScript(EmotionEnum.중립, GenreEnum.영화, "명랑", "김한민", "이순신", "아직도 살고자 하는 자가 있다니, 통탄을 금치 못할 일이다. 우리는 죽음을 피할 수 없다. 정녕 싸움을 피하는 것이 우리가 사는 길이냐? 육지라고 무사할듯 싶으냐? 똑똑히 봐라 나는 바다에서 죽고자 이곳을 불태운다. 더 이상 살 곳도 물러설 곳도 없다. 목숨을 기대지마라. 살고자 하면 필히 죽을 것이고 또한 죽고자 하면 살 것이니 병법에 이르기를 한 사람이 길목을 잘 지키면은 천 명의 적도 떨게 할수있다 하였다. 바로 지금 우리가 처한 형국을 두고 하는 말 아니더냐?", 0L);
            em.persist(script4);

            Script script5 = createScript(EmotionEnum.화남, GenreEnum.드라마, "더 글로리","김은숙", "하도영", "아내의 불륜 상대가 내 딸 학교에 가서 친부 행세를 했어. 그래서 화가 나, 받아쳐봐. 내가 아나 모르나 궁금하지? 예솔이가 내 딸인지 아닌지? 어 나 알아, 대꾸해 봐. 내가 어떤 마음으로 참고 있는데!!! 이 순간에도 넌 네가 얼마나 안전한지 그것만 궁금해? 예솔이가 그 지경인데! 네가 지금 날 떠봐?", 0L);
            em.persist(script5);

            Script script6 = createScript(EmotionEnum.슬픔, GenreEnum.영화, "쇼생크 탈출", "프랭크 다라본트", "앤디", "Remember Red, hope is good thing, maybe the best of things, and no good thing ever dies", 0L);
            em.persist(script6);

            Script script7 = createScript(EmotionEnum.기쁨, GenreEnum.영화, "포레스트 검프", "에릭 로스", "포레스트 검프", "Mama always said ... Life is like a box of chocolates. You never know what you're gonna get!", 0L);
            em.persist(script7);

            Script script8 = createScript(EmotionEnum.기쁨, GenreEnum.드라마, "오징어 게임", "황동혁", "일남", "우린 깐부잖아 깐부 사이에는, 네 거 내 거가 없는거야", 0L);
            em.persist(script8);

            Script script9 = createScript(EmotionEnum.두려움, GenreEnum.드라마, "오징어 게임", "황동혁", "일남", "제발 그만해! 이러다가는 다 죽어! 다 죽는 단 말이야!!!", 0L);
            em.persist(script9);

            Script script10 = createScript(EmotionEnum.화남, GenreEnum.드라마, "오징어 게임", "황동혁", "기훈", "사람이 죽었다고요! 내 말 안들려? 이러면 안 되는 거잖아! 우리끼리 죽이면 안 되는 거잖아", 0L);
            em.persist(script10);

            Script script11 = createScript(EmotionEnum.놀람, GenreEnum.드라마, "대장금", "김영현", "서장금", "예? 저는 제 입에서 고기를 씹을 때 홍시 맛이 났는데, 어찌 홍시라 생각했느냐 하시면, 그냥 홍시 맛이 나서 홍시라 생각한거 뿐인데...", 0L);
            em.persist(script11);
            
            Script script12 = createScript(EmotionEnum.슬픔, GenreEnum.드라마, "킬롤로지", "Gray Owen", "폴", "언덕을 내려가는 동안 발을 헛대뎠지만 넘어지지 않았어요. 아버지가 그 큰 손으로 날 꽉 잡아줬으니까", 0L);
            em.persist(script12);
            
            Script script13 = createScript(EmotionEnum.기쁨, GenreEnum.연극, "세자매", "안톤체홉", "이리나", "저는 오늘 왜 이렇게 즐거울까요? 마치 돛을 달고 미끄러지는 것 같아요! 머리 위에는 넓디넓은 푸른 하늘이 펼쳐지고 커다란 흰새가 날고 있어요. 무슨 일일까요?", 0L);
            em.persist(script13);
            
            Script script14 = createScript(EmotionEnum.슬픔, GenreEnum.뮤지컬, "HOPE", "강남", "호프", "집을 나선 나그네. 그에게 세상은 언제나 밤. 언제나 겨울. 봄이 오지 않아 얼어붙은 세상은 누구도 손잡을 수 없네", 0L);
            em.persist(script14);

        }

        public Script createScript(EmotionEnum emotion, GenreEnum genre, String title, String author, String actor, String content, Long viewCnt){
            Script script = new Script();
            script.setEmotion(emotion);
            script.setGenre(genre);
            script.setTitle(title);
            script.setAuthor(author);
            script.setActor(actor);
            script.setViewCnt(viewCnt);
            script.setContent(content);

            return script;
        }
    }
}
