package person.rootwhois.blog;

import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

/**
 * @Author: 陈广生
 * @Date: 2022/01/01/3:39 PM
 * @Description:
 */
public class JApiDocMain {
    public static void main(String[] args) {
        DocsConfig config = new DocsConfig();
        config.setProjectPath("./");
        config.setProjectName("个人博客管理系统");
        config.setApiVersion("v1.0");
        config.setDocsPath("./doc");
        config.setAutoGenerate(Boolean.TRUE);
        Docs.buildHtmlDocs(config);
    }
}
