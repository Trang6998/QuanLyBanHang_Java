<template>
    <v-app>
        <left-side-bar v-if="isLogin"></left-side-bar>
        <v-toolbar v-if="isLogin" style="background: #e46c0a;" dark app :clipped-left="$vuetify.breakpoint.lgAndUp" fixed>
            <v-toolbar-title style="width: 100%" class="ml-0 pl-3">
                <v-toolbar-side-icon @click.stop="show"></v-toolbar-side-icon>
                <a @click="goToMVCNet()" class="px-2">
                    <v-avatar v-if="user.AnhDaiDien !== ''">
                        <img style="min-width:62px;max-width:62px" src="/static/images/logo_1.png" />
                    </v-avatar>
                </a>
                <span class="hidden-sm-and-down">{{getTenChungCu()}}</span>
            </v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn class="gio" large flat style="color:#fff; margin-right:25px" to="/gio-hang">
                <v-icon style="font-size:35px">shopping_cart</v-icon>
                <div id="ex5">
                    <span class="p1 fa-stack fa-2x has-badge" :data-count="soLuong">
                    </span>
                </div>
                <h3>Giỏ hàng </h3>
            </v-btn>
            <v-menu offset-y>
                <v-btn icon large slot="activator" dark>
                    <v-avatar size="42px">
                        <img src="https://community.smartsheet.com/sites/default/files/default_user.jpg" />
                    </v-avatar>
                </v-btn>
                <v-list>
                    <v-list-tile to="">
                        <v-list-tile-title>
                            Thông tin tài khoản
                        </v-list-tile-title>
                    </v-list-tile>
                    <v-list-tile>
                        <v-list-tile-title>
                            Đổi mật khẩu
                        </v-list-tile-title>
                    </v-list-tile>
                    <v-list-tile @click="logout">
                        <v-list-tile-title>
                            Đăng xuất
                        </v-list-tile-title>
                    </v-list-tile>
                </v-list>
            </v-menu>
        </v-toolbar>
        <v-content>
            <v-container fluid fill-height grid-list-md>
                <v-layout justify-center>
                    <router-view></router-view>
                </v-layout>
                <vue-snotify></vue-snotify>
            </v-container>
        </v-content>
    </v-app>
</template>
<script lang="ts">
    import "vue-snotify/styles/material.css";
    import { Vue } from 'vue-property-decorator';
    import auth from '@/auth';
    import LeftSideBar from '@/components/Layout/LeftSideBar.vue';
    import { Users } from '@/models/Users';
    import DonDatHangApi, { DonDatHangApiSearchParams, GioHangApiSearchParams } from "./apiResources/DonDatHangApi";
    export default Vue.extend({
        name: 'App',
        components: { LeftSideBar },
        data() {
            return {
                clipped: false,
                fixed: true,
                miniVariant: false,
                right: true,
                rightDrawer: false,
                user: this.$store.state.user.User,
                searchParamsDonDatHang: { includeEntities: true, rowsPerPage: 0, taiKhoanKhachHangID: this.$store.state.user.User.userId } as GioHangApiSearchParams,
                soLuong: 0
            };
        },
        computed: {
            isLogin(): boolean {
                return this.$store.state.user.Token.IsAuthenticated;
            },
        },
        created() {
            this.getSoLuongSanPham(0);
            this.$eventBus.$on('UpdateGioHang', this.getSoLuongSanPham);
        },
        mounted() {
        },
        methods: {
            show() {
                this.$store.state.app.showLeftSideBar = !this.$store.state.app.showLeftSideBar
            },
            updateDrawer() {
                //let app = this.$store.state.app;
                //app.drawer = !this.$store.state.app.drawer;
                //this.$store.commit('UPDATE_APP', app);
            },
            getSoLuongSanPham(so: number) {
                DonDatHangApi.getgiohang(this.searchParamsDonDatHang).then(res => {
                    this.soLuong = (res as any).length;
                });
            },
            logout() {
                auth.logout();
            },
            goToMVCNet() {
                window.location.href = 'http://carevn.net/'
            },
            getTenChungCu() {
                if (this.user.ToaNhaId === 1) {
                    return 'Chung cư Sakura Tower'
                } else if (this.user.ToaNhaId === 2) {
                    return 'Chung cư CT1 Thạch Bàn'
                } else {
                    return 'Công ty CP chăm sóc bất động sản'
                }
            }
        }
    });
</script>
<style>

    #ex5 .p1[data-count]:after {
        position: absolute;
        right: 61%;
        top: -15%;
        content: attr(data-count);
        font-size: 14px;
        padding: .2em;
        border-radius: 50%;
        line-height: 1em;
        color: white;
        background: rgba(255,0,0,.85);
        text-align: center;
        min-width: 1.5em;
    }

    .v-toolbar__content {
        padding-left: 0px
    }

    #appDrawer {
        overflow: hidden;
    }

        #appDrawer .v-list__tile__action, #appDrawer .v-list__group__header .v-list__group__header__prepend-icon {
            min-width: 35px;
        }

        #appDrawer .v-list__group__header .v-list__group__header__prepend-icon {
            padding-right: 0;
        }

        #appDrawer .v-list__group__items--no-action .v-list__tile {
            padding-left: 35px;
        }

    .drawer-menu--scroll {
        height: calc(100vh - 100px);
        overflow: auto;
    }

    #headermenu .v-text-field.v-text-field--solo .v-input__control {
        min-height: 35px;
    }
    /* #headermenu .v-toolbar__content {
        background-image: url('/static/img/banner1.png'); background-size: 100% 100%;
    } */
    .fade-enter-active, .fade-leave-active {
        transition: opacity .5s;
    }

    .fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
        opacity: 0;
    }
</style>