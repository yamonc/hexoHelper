# Hexo文章转换器

> Hexo系列文章：
>
> 1. [从0到1部署博客系统到GitHub Page（0成本）](https://yamonc.github.io/2023/06/13/%E4%BB%8E0%E5%88%B01%E9%83%A8%E7%BD%B2%E5%8D%9A%E5%AE%A2%E7%B3%BB%E7%BB%9F%E5%88%B0Github-Page/)
> 2. [hexo如何修改主题？](https://yamonc.github.io/2023/06/13/hexo%E5%A6%82%E4%BD%95%E4%BF%AE%E6%94%B9%E4%B8%BB%E9%A2%98/)
> 3. [hexo-Acrylic主题集成twikoo评论](https://yamonc.github.io/2023/06/13/hexo-Acrylic%E4%B8%BB%E9%A2%98%E9%9B%86%E6%88%90twikoo%E8%AF%84%E8%AE%BA/)
> 4. [如何将JavaFX的java项目打包成exe格式](https://yamonc.github.io/2023/06/16/%E5%A6%82%E4%BD%95%E5%B0%86java%E9%A1%B9%E7%9B%AE%E6%89%93%E5%8C%85%E6%88%90exe%E6%A0%BC%E5%BC%8F/)

---------

## 背景

最近将博客平台使用Hexo搭建在Github上，但是如果每次发布文章的话，都需要设置一下front-matter的内容：

[Front-matter](https://hexo.io/zh-cn/docs/front-matter)

![image-20230619104358612](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306191043672.png)

感觉不太方便，所以想通过界面化的方式，将过程进行简化（要不，每次都手动在文章的开头写上---xxx---,太麻烦）。

基于此，本文通过界面化的方式，将文章进行格式化，通过代码的方式自动在文章开头插入markdown可识别的字符串。

## 示例

![image-20230619104639429](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306191046466.png)

使用该工具可以避免手动输入这些信息，而是通过界面化的方式进行配置：

![image-20230619104743062](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306191047100.png)

## 技术说明

Java17（开发语言）、Java FX（Java界面工具，类似于java8的swing，现已不支持java8）、SceneBuilder（拖拽式界面工具）、exe4j（jar转exe工具）。

踩坑无数，尤其是将java转为exe的过程中，踩了一晚上吧。

## 如何使用

下载地址：

[HexoHelper下载地址](https://gh.api.99988866.xyz/https://github.com/yamonc/hexoHelper/releases/download/V0.0.1/hexoHelper.exe)

下载好之后，无需安装过程，直接点击exe文件即可运行。

运行好之后，会出现[示例]中的界面。

### 功能说明

现在界面主要分为四个区域，从上到下以此为使用说明、选中文件区域、front-matter信息区域、功能区域。

这里主要讲解功能区域：

【上传文件】：点击上传文件按钮之后，选中待转换的markdown文件（没有front-matter信息的markdown文件）

【转换格式】：对已上传的文件进行格式转换，在文章头添加已经输入的front-matter信息。

【部署】：执行hexo clean、hexo g、hexo d一系列指令。

【一键部署】：对已上传的文件进行格式转换并执行hexo clean、hexo g、hexo d指令部署到对应平台上。

### 使用流程说明

![image-20230619105823619](https://markdown-image-bed.oss-cn-beijing.aliyuncs.com/202306191058659.png)

## 遗留问题

转换文章之后，题目乱码。

不够灵活，鲁棒性不好。对输入增加限制。

