<template>
  <div class="login">
    <div class="logo"></div>
    <div class="welcome-text">Welcome Notes Sharing Study System！</div>
    <div class="login-form">
      <el-form
        label-position="top"
        :model="loginInfo"
        size="small"
        class="form"
        ref="loginFormRef"
        :rules="loginFormRule"
      >
        <el-form-item label="User ID" class="item" prop="userId">
          <el-input v-model="loginInfo.userId" placeholder="userid">
            <i slot="prefix" class="el-input__icon el-icon-user-solid user"></i
          ></el-input>
        </el-form-item>
        <el-form-item label="Your Password" class="item" prop="password">
          <el-input
            v-model="loginInfo.password"
            type="password"
            placeholder="password"
            show-password
          >
            <i
              slot="prefix"
              class="el-input__icon iconfont el-icon-suo icon-suo suo"
            ></i>
          </el-input>
        </el-form-item>
      </el-form>

      <div class="form-btn">
        <el-button class="login-btn" round type="primary" @click="handleLogin">
          LOGIN
        </el-button>
      </div>

      <div class="forgot-password">
        <el-link>Forgot Your Password?</el-link>
      </div>

      <div class="not-account">
        <div>No Account Yet?</div>
        <el-link type="primary" href="/register">Sign Up</el-link>
      </div>
    </div>
  </div>
</template>

<script>
import { login } from "@/network/login/index.js";

export default {
  data() {
    return {
      loginInfo: {
        userId: "",
        password: "",
      },
      loginFormRule: {
        userId: [
          {
            required: true,
            message: "userId不能为空",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "密码不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    handleLogin() {
      this.$refs.loginFormRef.validate((valid) => {
        if (valid) {
          login(this.loginInfo).then((res) => {
            console.log(res);

            // save data
            this.$store.commit("SET_USER_INFO", res.data.user);
            this.$store.commit("SET_TOKEN", res.data.token);
            localStorage.setItem("token", JSON.stringify(res.data.token));
            localStorage.setItem("userInfo", JSON.stringify(res.data.user));
            localStorage.setItem(
              "refreshToken",
              JSON.stringify(res.data.refreshToken)
            );

            this.$router.push("/");
          });
        }
      });
    },
  },
};
</script>

<style lang="less">
.login {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
  background-image: linear-gradient(to top, #fff, #cce3f2);

  .logo {
    width: 43px;
    height: 43px;
    display: block;
    background: url(../../assets/logo.png) center no-repeat;
    background-size: 37px;
    border-radius: 10px;
    border: 1px solid #dcdcdc;
    background-color: #fff;
    margin-bottom: 25px;
  }

  .welcome-text {
    font-size: 23px;
    margin-bottom: 20px;
  }

  .login-form {
    background: #fff;
    border-radius: 5px;
    padding: 40px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    margin-bottom: 50px;

    .form {
      width: 260px;
    }

    .item {
      & > label {
        padding: 0px 0px 5px;
        font-weight: 900;
      }
      input {
        padding-left: 40px;
      }

      .user {
        color: #313746;
        border-right: 1px solid #eaebec;
        padding-right: 5px;
      }
      .suo {
        color: #313746;
        border-right: 1px solid #eaebec;
        padding-right: 5px;
      }
    }

    .form-btn {
      display: flex;
      margin-top: 24px;
      .login-btn {
        flex: 1;
      }
    }

    .forgot-password {
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 20px 0px;
      span {
        font-size: 12px;
        text-decoration: underline;
        text-decoration-color: #dbdbe1;
      }
    }

    .not-account {
      display: flex;
      justify-content: space-around;
      align-items: center;
      padding: 0px 30px;
      font-size: 12px;
      span {
        font-size: 12px;
        text-decoration: underline;
        text-decoration-color: #5a9cf8;
      }
    }
  }
}
</style>
