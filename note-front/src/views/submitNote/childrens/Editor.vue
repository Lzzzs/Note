<template>
  <mavon-editor
    id="editor"
    v-model="editorValue"
    :toolbars="toolbars"
    ref="md"
    @imgAdd="handleImgAdd"
    @imgDel="handleImgDel"
    language="en"
  ></mavon-editor>
</template>

<script>
import { saveNoteContentImage } from "@/network/note/index.js";
export default {
  model: {
    prop: "content",
    event: "updateContent",
  },
  props: {
    content: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      editorValue: "",
      toolbars: {
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: true, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: false, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: false, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
      },
      imgFiles: {},
    };
  },
  methods: {
    handleImgAdd(pos, file) {
      this.imgFiles[pos] = file;
    },
    handleImgDel(pos) {
      delete this.imgFiles[pos];
    },
    isNeedUpload() {
      return Object.keys(this.imgFiles).length != 0;
    },
    uploadimg() {
      const formdata = new FormData();
      for (const key in this.imgFiles) {
        formdata.append(
          "files",
          this.imgFiles[key],
          `${key}.${this.imgFiles[key].name.split(".")[1]}`
        );
      }
      debugger;

      /**
       * 返回promise，外面用await调用，保证上传完成之后，笔记中的图片地址已经换成了服务器的地址
       */
      return saveNoteContentImage(formdata).then((res) => {
        /**
         * res.data
         *    1.jpg: url
         *    2.jpg: url
         *    ...
         */
        for (const key in res.data) {
          this.$refs.md.$img2Url(key.split(".")[0], res.data[key]);
        }

        return true;
      });
    },
  },
  watch: {
    // v-model
    editorValue(value) {
      this.$emit("updateContent", value);
    },
  },
};
</script>

<style lang="less" scoped>
#editor {
  min-height: 400px;
}
</style>
