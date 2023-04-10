const { createProxyMiddleware } = require("http-proxy-middleware");

module.exports = function (app) {
  app.use(
    "/server",
    createProxyMiddleware({
      target: "http://j8b301.p.ssafy.io:8000/", // FastAPI 서버 주소
      changeOrigin: true, // 다른 도메인일 경우 필요
      pathRewrite: {
        "^/server": "", // 경로 설정
      },
    })
  );
};