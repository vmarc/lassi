PROXY_CONFIG = {
  "/scala-api/**": {
    target: "http://localhost:8081",
    secure: false,
    logLevel: 'debug'
  },
  "/api/**": {
    target: "http://localhost:8080",
    secure: false,
    logLevel: 'debug'
  },
  "/json-api/**": {
    "target": "http://localhost:9005",
    "secure": false
  },
  "/tiles/**": {
    "target": "https://experimental.knooppuntnet.nl",
    "secure": false
  },
  "**": {
    "target": "http://localhost:9000",
    "secure": false,
    "bypass": function (req) {
      console.log("URL " + req.url);
      if (req.headers.accept.indexOf("html") !== -1) {
        return "/index.html";
      }
    }
  }
};

module.exports = PROXY_CONFIG;
