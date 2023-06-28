# Hexo文章转换器II



> Hexo系列文章：
>
> 1. [从0到1部署博客系统到GitHub Page（0成本）](https://yamonc.github.io/2023/06/13/%E4%BB%8E0%E5%88%B01%E9%83%A8%E7%BD%B2%E5%8D%9A%E5%AE%A2%E7%B3%BB%E7%BB%9F%E5%88%B0Github-Page/)
> 2. [hexo如何修改主题？](https://yamonc.github.io/2023/06/13/hexo%E5%A6%82%E4%BD%95%E4%BF%AE%E6%94%B9%E4%B8%BB%E9%A2%98/)
> 3. [hexo-Acrylic主题集成twikoo评论](https://yamonc.github.io/2023/06/13/hexo-Acrylic%E4%B8%BB%E9%A2%98%E9%9B%86%E6%88%90twikoo%E8%AF%84%E8%AE%BA/)
> 4. [如何将JavaFX的java项目打包成exe格式](https://yamonc.github.io/2023/06/16/%E5%A6%82%E4%BD%95%E5%B0%86java%E9%A1%B9%E7%9B%AE%E6%89%93%E5%8C%85%E6%88%90exe%E6%A0%BC%E5%BC%8F/)

-------

## 背景

该工具可以帮助你在使用Hexo写博客的时候，将文章直接转换成为Hexo可识别并可展示在页面；

每次在写博客的时候，都需要手动在文章的开头添加Front-Matter的信息：

![image-20230619104358612](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306281633297.png)

感觉不太方便，所以想通过界面化的方式，将过程进行简化（要不，每次都手动在文章的开头写上---xxx---,太麻烦）。

基于此，本文通过界面化的方式，将文章进行格式化，通过代码的方式自动在文章开头插入markdown可识别的字符串。

------

⭐⭐⭐⭐⭐V1.0.0更新日志

第一版的Hexo文章转换器使用起来不太方便，所以在第一版的基础上进行了更新，主要是将标签和分类的信息保存到sqlite数据库中，不再用户每次都手动输入了。（初版的思路是，标题、 创建时间、位置等一些信息可以从后台直接获取，比如标题可以通过读取文章的第一行获取，然后将标题生成到原文中就行，不知道为什么打包成exe格式之前，标题都能从文章中提取出来，但是在exe中如果获取出来的标题永远都是乱码，各种尝试都无用，无奈，手动输入标题吧）

## 示例

![image-20230628164814150](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306281648185.png)

使用该工具可以避免手动输入这些信息，而是通过界面化的方式进行配置：

![image-20230628164828060](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306281648109.png)

但是，**建议在使用之前进行两项的配置**：

![image-20230628164907063](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306281649108.png)

工作目录：主要是基于这个目录，执行一系列指令：hexo clean、hexo g和hexo d等指令。

上传文件目录：在Hexo转换器Tab中，需要上传文件之后才能点转换，这里的位置就是点击后重定向的位置，换句话说，就是你平常文章经常放的位置，通常是_posts。

![image-20230628165125467](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306281651512.png)

关于Hexo转换器界面，主要就是讲解这个页面是干什么用的，解决了什么问题。

## 技术说明

Java17（开发语言）、Java FX（Java界面工具，类似于java8的swing，现已不支持java8）、SceneBuilder（拖拽式界面工具）、exe4j（jar转exe工具）。

踩坑无数，尤其是将java转为exe的过程中，踩了一晚上吧。

## 如何使用

下载地址：

[HexoHelper下载地址](https://github.com/yamonc/hexoHelper/releases/download/V1.0.0/hexoHelperV1.0.0.zip)

下载好之后，解压缩，解压缩之后，需要注意的是，db文件夹要和hexoHelper.exe是同级目录，db文件夹中是sqlite的数据库，运行的时候需要找到db的数据库才行。最终文件的位置关系：

![image-20230628165640833](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306281656872.png)

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

![image-20230619105823619](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306281633232.png)

## 待优化问题

1. 用户使用优化。
2. 标签和分类的操作业务流程有问题。无法删除标签和分类。考虑在设置中体现。
3. 显示优化，如果cover或者其他字段太长的话，会影响到字体显示。



