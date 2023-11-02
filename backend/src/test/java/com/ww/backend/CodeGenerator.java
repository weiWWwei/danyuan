package com.ww.backend;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        String url ="jdbc:mysql://localhost:3306/sf";
        String username = "root";
        String password = "ww123456";
        String moduleName =  "sys";
        String mapperLocation ="E:\\newStudent\\backend\\src\\main\\resources\\mapper\\";
        String tables = "x_user,x_role,x_menu,x_user_role,x_role_menu,x_student";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("ww") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E:\\newStudent\\backend\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ww.backend") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix("x_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
