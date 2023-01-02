<template>
  <div class="submit-note-content">
    <div class="title">
      <span class="require">Note Title </span>
      <el-input
        style="width: 350px"
        type="text"
        placeholder="please input note title"
        v-model="title"
        maxlength="20"
        show-word-limit
      >
      </el-input>
    </div>

    <div class="tag">
      <span class="require">Note Tag </span>

      <el-select
        v-model="tagValues"
        multiple
        placeholder="please select tag"
        style="width: 350px"
      >
        <el-option v-for="item in tagOptions" :key="item" :value="item">
        </el-option>
      </el-select>
    </div>
    <div class="content">
      <div class="content-title require">Note Content</div>
      <Editor v-model="noteContent" ref="editor" />
    </div>

    <div class="upload">
      <span class="require">Note Image</span>
      <el-upload
        ref="upload"
        :action="uploadUrl"
        :file-list="fileList"
        :auto-upload="false"
        :before-upload="checkFile"
        accept=".jpg,.png,.jpeg"
        :http-request="submitNoteData"
        :limit="1"
      >
        <el-button size="small" type="primary">post note image</el-button>
        <div slot="tip" class="el-upload__tip">
          Only a single image in jpg/png/jpeg format can be uploaded and the
          maximum size is 10MB
        </div>
      </el-upload>
    </div>

    <div class="submit">
      <el-button type="primary" @click="handleSubmitNote"
        >submit note</el-button
      >
    </div>
  </div>
</template>

<script>
import Editor from './Editor.vue'
import { saveNoteData } from '@/network/note/index.js'

export default {
  data() {
    return {
      title: '',
      tagValues: [],
      noteContent: '',
      tagOptions: ['HTML', 'MySQL', 'PHP', 'Java', 'Python'],
      uploadUrl: '',
      fileList: [],
      fileNameMatch: ['jpeg', 'jpg', 'png']
    }
  },
  components: { Editor },
  methods: {
    /**
     * 实际提交数据方法
     */
    async submitNoteData(param) {
      if (this.$refs.editor.isNeedUpload()) {
        // 如果笔记内有图片，那么先进行图片上传
        await this.$refs.editor.uploadimg()
      }

      // 对多个tag进行转换数据格式
      const tags = this.tagValues.join(',')

      const formData = new FormData()

      // 如果地址栏上有groupId，证明是分组内提交的
      const id = this.$route?.query.groupId
      if (id) formData.append('groupId', id)

      formData.append('noteImg', param.file)
      formData.append('title', this.title)
      formData.append('content', this.noteContent)
      formData.append('tag', tags)
      formData.append('userId', this.$store.state.userInfo.userid)

      saveNoteData(formData).then(() => {
        this.$message.success('笔记发布成功')
        id
          ? this.$router.push(`/group?id=${id}&page=1`)
          : this.$router.push('/')
      })
    },
    validateNoteInfo() {
      if (
        this.title == '' ||
        this.tagValues.length === 0 ||
        this.noteContent == '' ||
        this.$refs.upload.uploadFiles.length == 0
      ) {
        this.$message.warning('信息未填完整，请检查')
        return false
      }
      return true
    },
    handleSubmitNote() {
      if (this.validateNoteInfo()) {
        // 调用upload组件的上传方法，在它内部做提交
        this.$refs.upload.submit()
      }
    },
    checkFile(file) {
      //上传之前的操作，file上传的文件对象
      let name = file.name
      if (name.lastIndexOf('.') == -1) {
        this.$message.warning('文件格式不正确')
        return false
      } else {
        //获取后缀
        let suff = name.substring(name.lastIndexOf('.') + 1)
        var count = 0
        //遍历提前定义好的后缀数组
        for (var i in this.fileNameMatch) {
          //如果有匹配的(注意大小写问题)
          if (this.fileNameMatch[i].toLowerCase() == suff.toLowerCase()) {
            count++
            //匹配到就退出循环
            break
          }
        }
        //如果没匹配到
        if (count == 0) {
          this.$message.warning('只能上传jpg,png,jpeg格式的图片')
          return false
        }
      }

      //判断文件大小
      if (file.size > 1024 * 1024 * 10) {
        this.$message.warning('文件大小不能超过10MB')
        return false
      }
    }
  }
}
</script>

<style lang="less" scoped>
.submit-note-content {
  flex: 1;
  background: #fff;
  border-radius: 5px;
  border: 1px solid #dcdcdc;
  border-bottom: none;
  padding: 10px;

  .require {
    &::before {
      content: '*';
      color: #f56c6c;
      margin-right: 4px;
    }
  }

  .title {
    & > span {
      margin-right: 20px;
    }
    margin-bottom: 15px;
  }

  .tag {
    & > span {
      margin-right: 24px;
    }
    margin-bottom: 15px;
  }

  .content {
    .content-title {
      margin-bottom: 10px;
    }
    margin-bottom: 15px;
  }
  .upload {
    & > span {
      margin-right: 24px;
      margin-top: 5px;
    }
    display: flex;
    align-items: flex-start;
    margin-top: 5px;
    height: 80px;
    margin-bottom: 5px;
  }

  .submit {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
