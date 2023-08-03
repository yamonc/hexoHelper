---
title: Hexo文章转换器III
date: 2023-08-03 17:17:01
tags: [Java, 工具]
categories: [开源项目]
recommend: true
locate: [天津]
cover: https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202308031551988.jpeg
comment: 是
keywords:
password:
abstract: 有东西被加密了, 请输入密码查看.
message: 您好, 这里需要密码.
wrong_pass_message: 抱歉, 这个密码看着不太对, 请再试试.
wrong_hash_message: 抱歉, 这个文章不能被校验, 不过您还是能看看解密后的内容.

---

# Hexo文章转换器III

![a person standing on top of a cliff](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202308031551988.jpeg)

>Hexo系列文章：
>
>1. [从0到1部署博客系统到GitHub Page（0成本）](https://yamonc.github.io/2023/06/13/%E4%BB%8E0%E5%88%B01%E9%83%A8%E7%BD%B2%E5%8D%9A%E5%AE%A2%E7%B3%BB%E7%BB%9F%E5%88%B0Github-Page/)
>2. [hexo如何修改主题？](https://yamonc.github.io/2023/06/13/hexo%E5%A6%82%E4%BD%95%E4%BF%AE%E6%94%B9%E4%B8%BB%E9%A2%98/)
>3. [hexo-Acrylic主题集成twikoo评论](https://yamonc.github.io/2023/06/13/hexo-Acrylic%E4%B8%BB%E9%A2%98%E9%9B%86%E6%88%90twikoo%E8%AF%84%E8%AE%BA/)
>4. [如何将JavaFX的java项目打包成exe格式](https://yamonc.github.io/2023/06/16/%E5%A6%82%E4%BD%95%E5%B0%86java%E9%A1%B9%E7%9B%AE%E6%89%93%E5%8C%85%E6%88%90exe%E6%A0%BC%E5%BC%8F/)
>5. [Hexo文章转换器](https://yamonc.github.io/2023/06/19/02.%E5%BC%80%E6%BA%90%E9%A1%B9%E7%9B%AE/Hexo%E6%96%87%E7%AB%A0%E8%BD%AC%E6%8D%A2%E5%99%A8/)
>6. [Hexo文章转换器II](https://yamonc.github.io/2023/06/28/02.%E5%BC%80%E6%BA%90%E9%A1%B9%E7%9B%AE/Hexo%E6%96%87%E7%AB%A0%E8%BD%AC%E6%8D%A2%E5%99%A8II/)

## 更新记录

⭐⭐⭐⭐V1.0.0更新日志

第一版的Hexo文章转换器使用起来不太方便，所以在第一版的基础上进行了更新，主要是将标签和分类的信息保存到sqlite数据库中，不再用户每次都手动输入了。（初版的思路是，标题、 创建时间、位置等一些信息可以从后台直接获取，比如标题可以通过读取文章的第一行获取，然后将标题生成到原文中就行，不知道为什么打包成exe格式之前，标题都能从文章中提取出来，但是在exe中如果获取出来的标题永远都是乱码，各种尝试都无用，无奈，手动输入标题吧）

⭐⭐⭐⭐⭐V1.1.0更新日志-小版本更新

V1.1.0版本在界面上进行了一系列的改动，优化了用户体验，主要体现在：

1. 显示内容正常无遮挡。上一版本由于页面布局的问题，cover一般都会很长，导致遮挡后面部分label。
2. 新增一键重置功能，考虑到有可能一次性转换多个文章，如果不重置的话，无法重新上传文件并转换，只能关掉应用程序重新打开，才能进行转换新的文章。
3. 新增标签管理和分栏管理Tab页。在最顶端新增两个Tab页，分别用来对标签和分栏的新增和删除功能。
4. 优化了大部分的代码，包括但不限于接口实现jdbc操作数据库功能，提高易用性和扩展性、新增表结构、合适的地方处理异常等等。

⭐⭐⭐⭐⭐V2.0.0更新日志

新增输入密码才可查看文章。（解决因文章版权问题或者不想让别人查看指定文章）

可自定义输入密码提示语、及密码错误提示语，设置自定义密码等功能。

## 背景

通常在使用Hexo写文章的时候，都需要将在Hexo文章的开头处，加上Front-Matter的必要信息，用于Hexo识别并提取出文章的meta信息。

但是，每次写都不是特别方便，所以，使用界面化的方式来简化操作步骤，通过点击的方式对使用过程进行了简化。

适用hexo主题为[Hexo Acrylic](https://next-docs.acrylic.org.cn/)，并且使用[hexo-blog-encrypt](https://github.com/D0n9X1n/hexo-blog-encrypt)对文章进行加密。

## 示例

使用该工具可以避免手动输入这些信息，而是通过界面化的方式进行配置：

![image-20230803160906642](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202308031609520.png)

但是，**建议在使用之前进行三项的配置**，其中上传目录是必须的，**要不无法使用**

![image-20230803160919907](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202308031609948.png)

管理标签界面：

![image-20230803160927878](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202308031609839.png)

分栏管理界面：

![image-20230803160932865](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202308031609583.png)

关于我界面：

![image-20230803160938395](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202308031609976.png)

转换之后的界面：

![image-20230803160838891](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202308031610916.png)

## 技术说明

Java17（开发语言）、Java FX（Java界面工具，类似于java8的swing，现已不支持java8）、SceneBuilder（拖拽式界面工具）、exe4j（jar转exe工具）。

踩坑无数，尤其是将java转为exe的过程中，踩了一晚上吧。

## 如何使用

下载地址：

[HexoHelper下载地址](https://github.com/yamonc/hexoHelper/releases/download/V1.0.0/hexoHelperV1.0.0.zip)

下载好之后，解压缩，解压缩之后，需要注意的是，db文件夹要和hexoHelper.exe是同级目录，db文件夹中是sqlite的数据库，运行的时候需要找到db的数据库才行。最终文件的位置关系：

![image-20230628165640833](D:\blog\source\_posts\02.开源项目\assets\202306281656872.png)

开始运行，会出现[示例]中的界面。

### 功能说明

现在的界面主要分三个主Tab：

#### Hexo转换器

该界面主要分三个部分：

从上到下依次是Front-Matter清单，在这里可以看到你填写的一些文章信息，这也是最终会填写到你文章里面的内容；

输入区：在这里你需要输入一些文章信息，这个和Front-Matter清单联动，修改了什么会体现在清单上。

最下面是功能区域：

【上传文件】：点击上传文件按钮之后，选中待转换的markdown文件（没有front-matter信息的markdown文件）

【转换格式】：对已上传的文件进行格式转换，在文章头添加已经输入的front-matter信息。

【部署】：执行hexo clean、hexo g、hexo d一系列指令。

【一键部署】：对已上传的文件进行格式转换并执行hexo clean、hexo g、hexo d指令部署到对应平台上。

#### 设置

主要设置一些提升用户体验的必要信息。

工作目录：主要是基于这个目录，执行一系列指令：hexo clean、hexo g和hexo d等指令。

上传文件目录：在Hexo转换器Tab中，需要上传文件之后才能点转换，这里的位置就是点击后重定向的位置，换句话说，就是你平常文章经常放的位置，通常是_posts。

#### 关于Hexo转换器

主要介绍了该工具解决了什么问题，如何使用及关于作者等信息。

## 使用流程说明

![image-20230619105823619](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202308031613532.png)

## 待优化问题

现在主要存在三个核心问题暂时没有解决：

第一，使用sqlite作为持久化数据库，但是sqlite文件必须在外部挂载，无法将sqlite打到jar包中，并转成exe文件。

第二，出错没有日志可查，可以将日志输出成文件，并保存在客户端。

第三，利用公私钥的方式对密码进行加密，并输入在文章的Front-Matter上。

