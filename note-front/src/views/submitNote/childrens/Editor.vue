<template>
  <mavon-editor
    id="editor"
    v-model="editorValue"
    :toolbars="editorConf"
    ref="md"
    @imgAdd="handleImgAdd"
    @imgDel="handleImgDel"
    language="en"
  ></mavon-editor>
</template>

<script>
import { saveNoteContentImage } from '@/network/note/index.js'
import editorConf from './editorConf'

export default {
  model: {
    prop: 'content',
    event: 'updateContent'
  },
  props: {
    content: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      editorValue: '',
      imgFiles: {},
      editorConf
    }
  },
  methods: {
    handleImgAdd(pos, file) {
      this.imgFiles[pos] = file
    },
    handleImgDel(pos) {
      delete this.imgFiles[pos]
    },
    isNeedUpload() {
      return Object.keys(this.imgFiles).length != 0
    },
    uploadimg() {
      const formdata = new FormData()
      for (const key in this.imgFiles) {
        formdata.append(
          'files',
          this.imgFiles[key],
          `${key}.${this.imgFiles[key].name.split('.')[1]}`
        )
      }

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
          this.$refs.md.$img2Url(key.split('.')[0], res.data[key])
        }

        return true
      })
    }
  },
  watch: {
    // v-model
    editorValue(value) {
      this.$emit('updateContent', value)
    }
  }
}
</script>

<style lang="less" scoped>
#editor {
  min-height: 400px;
}
</style>
