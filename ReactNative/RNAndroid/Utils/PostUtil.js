function renderPost(postcontent) {
    var template =
        ` <!DOCTYPE html>
        <html>
                <head>
                <meta charset="utf-8"> <meta name="viewport" content="width=device-width,initial-scale=1.0,user-scalable=no">
                
                <style>
                body {
                    overflow: auto;
                    font-family: -apple-system,SF UI Text,Arial,PingFang SC,Hiragino Sans GB,Microsoft YaHei,WenQuanYi Micro Hei,sans-serif;
                }
                h2 {
                    font-size: 20px;
                }
                .content h1, .content h2, .content h3, .content h4, .content h5, .content h6 {
                    margin: 0 0 15px;
                    font-weight: 700;
                    color: #2f2f2f;
                    line-height: 1.7;
                    text-rendering: optimizelegibility;
                }

                .content{
                    max-height:100%;
                    color: #2D2828;
                    overflow:hidden,
                    font-weight: 400;
                    line-height: 1.7;
                    margin-bottom:20px;
                }

                div {
                    max-width:100%,
                    overflow:auto,
                    display: block;

                }
                .wp-caption {
                    
                    text-align: center;
                    max-width: 100%;
                    padding: 4px;
                }
                .aligncenter {
                    clear: both;
                    display: block;
                    margin-left: auto;
                    margin-right: auto;
                }
                .wp-caption-text{
                    font-style: italic;
                        text-align: center;
                       min-width: 20%;
                        max-width: 80%;
                        min-height: 22px;
                        display: inline-block;
                        padding: 10px 10px 6px;
                        margin: 0 auto;
                        border-bottom: 1px solid #d9d9d9;
                        font-size: 14px;
                        color: #969696;
                        line-height: 1.7;
                }
                .content > p {
                word-break: break-word;
                    position: relative;
                    color: #202020;
                    margin: 14px 0;
                    padding: 0 11px;
                    font-size: 17px;
                    line-height: 26px;
                   
                }
                pre ,code{
                   
                       white-space: pre-wrap;
                    margin: 1em 0px 1em;
                    padding: 12px;
                    overflow: auto;
                    border: 1px solid #F2C8A4;
                    margin-bottom: 20px;
                    font-size: 13px;
                    border-radius: 0;
                    background-color: #f6f6f6;
                    color: #6298AC;
                    
                }

                blockquote {
                    margin: 20px;
                    padding: 8px 20px 8px 20px;
                    background-color: #f7f7f7;
                    border-left: 6px solid #B1BBC6;
                    word-break: break-word;
                    font-size: 15px;
                    line-height: 24px;
                }

                img {
                    max-width: 98%;
                    height: auto;
                    vertical-align: middle;
                    border: 0;
                    cursor: -webkit-zoom-in;
                    transition: all .25s ease-in-out;
                }
                .content ul li {
                    line-height: 30px;
                }

                ul {
                    padding: 0;
                    margin-left: 22px;
                    margin-bottom: 20px;
                }
                a{
                    word-wrap:break-word;
                    color: #007ed8;
                    text-decoration: none;
                    background: transparent;
                    margin: 0;
                    padding: 0;
                    vertical-align: baseline;
                    -webkit-transition: all 0.2s ease;
                    -moz-transition: all 0.2s ease;
                    transition: all 0.2s ease;
                }
                table {
                    max-width:90%;
                    margin-bottom: 20px;
                    border: 1px solid #ddd;
                    border-collapse: collapse;
                    overflow:hidden;
                }
                Header.header{
                    border-left: 4px #e94d3d solid;
                    margin-bottom: 20px;
                }
                Header .page-view-article-title {
                    text-align: left;
                    font-weight: 700;
                    color: #000;
                    font-size: 20px;
                    line-height: 27px;
                    padding: 0 0 5px 7px;
                    position: relative;
                    top: -2px;
                    margin-right: 10px;
                }
                Header .page-view-article-info {
                    text-align: left;
                    color: #8b8b8b;
                    font-size: 12px;
                    line-height: 14px;
                    padding: 2px 11px 0 7px;
                }
                Header .page-view-article-info-time {
                    margin-left: 12px;
                }


                </style>
                </head>
                <body>
                <Header class="header">
                    <h1 class="page-view-article-title">{{title}}</h1>
                    <h3 class="page-view-article-info"> {{category}} <span class="page-view-article-info-time">{{time}}</span>
                    </h3>
                 </Header>
                <div class="content">
                {{content}}
                </div>
       
  

                </body>
            </html>
                `;

    // var elementNames=["content","title","time","category"];
    var reg = new RegExp("{{content}}", "g"); //创建正则RegExp对象  
    var renderedStr = template.replace(reg, post_html_decode(postcontent.content.rendered));
    reg = new RegExp("{{title}}", "g"); //创建正则RegExp对象  
    renderedStr = renderedStr.replace(reg, postcontent.title.rendered);
    var TimeUtil = require("./Time")
    reg = new RegExp("{{time}}", "g"); //创建正则RegExp对象  
    renderedStr = renderedStr.replace(reg, new TimeUtil().getTimeString(postcontent.date));

    reg = new RegExp("{{category}}", "g"); //创建正则RegExp对象  
    renderedStr = renderedStr.replace(reg, (postcontent.category));

    //renderedStr = 
    // console.log(renderedStr)
    return renderedStr;

}
function html_encode(str) {
    var s = "";
    if (str.length == 0) return "";
    s = str.replace(/&/g, "&gt;");
    s = s.replace(/</g, "&lt;");
    s = s.replace(/>/g, "&gt;");
    s = s.replace(/ /g, "&nbsp;");
    s = s.replace(/\'/g, "&#39;");
    s = s.replace(/\"/g, "&quot;");
    s = s.replace(/\n/g, "<br>");
    return s;
}
function post_html_decode(s) {
    //var s = "";   
    if (s.length == 0) return "";

    var pres = s.match(/<pre[\s\S]*?>([\s\S]*?)<\/pre>/g);
    if (pres)
        for (var i = 0; i < pres.length; i++) {
            var pre = pres[i].replace(/&amp;/g, "&").replace(/\n/g, "<br>").replace(/\r/g, "&nbsp;&nbsp;&nbsp;")
            s = s.replace(pres[i], pre);
        }

    //console.log(s);
    return s;
}
var PostRender = renderPost
module.exports = PostRender