<template>
    <v-container fluid fill-height>
        <v-layout align-center wrap justify-center class="login_page_wrapper">
            <v-flex xs12 sm4></v-flex>
            <v-flex xs12 sm4 class="text-xs-center">
                <v-card class="elevation-12">
                    <v-card-text>
                        <v-form>
                            <v-text-field v-model="credentials.username"
                                          @keypress.enter="submit"
                                          prepend-icon="person"
                                          label="Tài khoản"
                                          type="text"
                                          :error-messages="errors.collect('Tài khoản')"
                                          name="Tài khoản"
                                          data-vv-scope="formLogin"
                                          v-validate="'required'"
                                          required></v-text-field>
                            <v-text-field v-model="credentials.password"
                                          :append-icon="show1 ? 'visibility' : 'visibility_off'"
                                          :type="show1 ? 'text' : 'password'"
                                          @click:append="show1 = !show1"
                                          @keypress.enter="submit"
                                          prepend-icon="vpn_key"
                                          label="Mật khẩu"
                                          required
                                          :error-messages="errors.collect('Mật khẩu')"
                                          v-validate="'required|min: 6'"
                                          name="Mật khẩu"
                                          data-vv-scope="formLogin"></v-text-field>
                        </v-form>
                        <v-btn block color="primary" :loading="loggingIn" :disabled="loggingIn" @click="submit">Đăng nhập</v-btn>
                        <v-layout row wrap>
                            <v-flex xs6>
                                <v-checkbox label="Ghi nhớ?"
                                            v-model="credentials.remember" hide-details></v-checkbox>
                            </v-flex>
                        </v-layout>
                    </v-card-text>
                </v-card>
            </v-flex>
            <v-flex xs12 sm4></v-flex>
        </v-layout>
    </v-container>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import auth from '@/auth';
    export default Vue.extend({
    $_veeValidate: {
      validator: 'new'
    },
    name: 'login',
    data () {
        return {
            show1: false,
            credentials: {
                username: '',
                remember: false,
                password: ''
            },
            loggingIn: false,
            msg: '',
            isLogin: false
        }
    },
    methods: {
        submit() {
            this.loggingIn = true;
            auth.login(this.credentials, 'danh-muc-san-pham')
                .then((response: any) => {
                    this.loggingIn = false;
                    if (!this.$store.state.user.Token.IsAuthenticated) {
                        this.$snotify.error('Sai tên đăng nhập hoặc mật khẩu.');
                    }
                    else {
                        this.$router.push({ path: 'danh-muc-san-pham' })
                        this.isLogin = true;
                    }
                })
                .catch((errorResponse: any) => {
                    this.loggingIn = false;
                    if (errorResponse !== 'Login failed!') {
                        this.msg = 'Có lỗi xảy ra.';
                    }
                    else {
                        this.msg = 'Tài khoản hoặc mật khẩu không chính xác.'
                    }
                    this.$snotify.error(this.msg);
                });
        },
      CheckAuthIsLogin () {
          if (this.$store.state.user.Token.IsAuthenticated) {
              this.$router.push({ path: 'danh-muc-san-pham' })
              this.isLogin = true;
          }
          else
              this.isLogin = false
      }
    },
    mounted () {
    // Auth.logout()
    },
    created () {
      this.isLogin = false
      this.CheckAuthIsLogin()
    }
  })

</script>
<style lang="css" scoped>
  body {
      font-size: 15px;
      padding: 64px 24px;
      overflow-y: auto;
      height: auto;
      min-height: 100%;
  }
  .login_page_wrapper {
      width: 360px;
      max-width: 100%;
      margin: 0 auto;
      -webkit-transition: all 280ms cubic-bezier(.4,0,.2,1);
      transition: all 280ms cubic-bezier(.4,0,.2,1);
  }
  .md-input-container input, .md-input-container textarea {
    height: auto;
  }
  #page_content{
    margin-right:0
  }
</style>
