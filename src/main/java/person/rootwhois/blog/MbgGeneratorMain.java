package person.rootwhois.blog;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: 陈广生
 * @Date: 2022/01/01/4:34 PM
 * @Description:
 */
public class MbgGeneratorMain {
    public static void main(String[] args) {
        List<String> tables = new ArrayList<>();
        tables.add("admin");
        tables.add("article");
        tables.add("article_sorts");
        tables.add("article_tags");
        tables.add("comment");
        tables.add("history");
        tables.add("sort");
        tables.add("tag");
        tables.add("web");

        FastAutoGenerator.create("jdbc:mysql:///blog?useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true","root","root12138")
                .globalConfig(builder -> {
                    builder.author("陈广生")               //作者
                            .outputDir(System.getProperty("user.dir")+"/mbg")    //输出路径(写到java目录)
                            .enableSwagger()           //开启swagger
                            .commentDate("yyyy-MM-dd");
//                            .fileOverride();            //开启覆盖之前生成的文件

                })
                .packageConfig(builder -> {
                    builder.parent("person.rootwhois.blog")
                            .moduleName("")
                            .entity("entity")
                            .service("service")
                            .serviceImpl("service.impl")
                            .controller("controller")
                            .mapper("dao")
                            .xml("mapper")
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,System.getProperty("user.dir")+"mbg/resources/mapper"));
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables)
                            .addTablePrefix("")
                            .serviceBuilder()
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .entityBuilder()
                            .addTableFills(new Column("create_time", FieldFill.INSERT))
                            .addTableFills(new Column("update_time", FieldFill.INSERT_UPDATE))
                            .versionColumnName("version")
                            .enableLombok()
                            .logicDeleteColumnName("deleted")
                            .enableTableFieldAnnotation()
                            .controllerBuilder()
                            .formatFileName("%sController")
                            .enableRestStyle()
                            .mapperBuilder()
                            .superClass(BaseMapper.class)
                            .formatMapperFileName("%sDao")
                            .enableMapperAnnotation()
                            .formatXmlFileName("%sMapper")
                            .enableBaseColumnList()
                            .enableMapperAnnotation()
                            .enableBaseResultMap();
                })
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
