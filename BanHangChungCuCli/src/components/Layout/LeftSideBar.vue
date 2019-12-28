<template>
    <v-navigation-drawer persistent :mini-variant="miniVariant"
                         width="250"
                        :clipped="$vuetify.breakpoint.lgAndUp" id="appDrawer" style="position:fixed; top:0; left:0; overflow-y:auto;"
                         :value="showLeftSideBar" @input="toggleSidebar"
                         enable-resize-watcher fixed app>
        <v-list id="leftSideBar">
            <v-list dense expand>
                <template v-if="menus.length > 0" v-for="(item, i) in menus">
                    <!--group with subitems-->
                    <v-list-group v-if="item.items && item.show" :key="item.title" :prepend-icon="item.icon" no-action="no-action">
                        <v-list-tile slot="activator" ripple="ripple" v-if="item.title && item.show">
                            <v-list-tile-content>
                                <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                            </v-list-tile-content>
                        </v-list-tile>
                        <template v-for="(subItem, i) in item.items">
                            <!--sub group-->
                            <v-list-group v-if="subItem.items && subItem.show" :key="subItem.name" sub-group="sub-group">
                                <v-list-tile slot="activator" ripple="ripple" v-if="subItem.title && subItem.show">
                                    <v-list-tile-content>
                                        <v-list-tile-title>{{ subItem.title }}</v-list-tile-title>
                                    </v-list-tile-content>
                                </v-list-tile>
                            </v-list-group>
                            <!--child item-->
                            <v-list-tile v-else-if="subItem.show" :key="i" :to="subItem.link" ripple="ripple">
                                <v-list-tile-content>
                                    <v-list-tile-title><span>{{ subItem.title }}</span></v-list-tile-title>
                                </v-list-tile-content>
                                <v-list-tile-action v-if="subItem.action">
                                    <div id="ex4">
                                        <span class="p1 fa-stack fa-2x has-badge" :data-count="tongDonChuaNhan">
                                        </span>
                                    </div>
                                    <!--<v-icon :class="[subItem.actionClass || 'success--text']">{{ subItem.action }}</v-icon>-->
                                </v-list-tile-action>
                            </v-list-tile>
                        </template>
                    </v-list-group>
                    <!--top-level link-->
                    <v-list-tile v-else-if="item.show" :to="item.link" ripple="ripple" rel="noopener" :key="item.link">
                        <v-list-tile-action v-if="item.icon && item.show">
                            <v-icon>{{ item.icon }}</v-icon>
                        </v-list-tile-action>
                        <v-list-tile-content v-if="item.title && item.show">
                            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                        </v-list-tile-content>
                        <!-- <v-circle class="white--text pa-0 chip--x-small" v-if="item.badge" :color="item.color || 'primary'" disabled="disabled">{{ item.badge }}</v-circle> -->
                        <v-list-tile-action v-if="item.subAction && item.show">
                            <v-icon class="success--text">{{ item.subAction }}</v-icon>
                        </v-list-tile-action>
                        <!-- <v-circle class="caption blue lighten-2 white--text mx-0" v-else-if="item.chip" label="label" small="small">{{ item.chip }}</v-circle> -->
                    </v-list-tile>
                </template>
            </v-list>
        </v-list>
    </v-navigation-drawer>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import { HTTP } from '@/HTTPServices.ts';
    import DonDatHangApi, { DonDatHangApiSearchParams } from '@/apiResources/DonDatHangApi';
    import * as MUTATION_TYPES from '../../store/MUTATION_TYPES'
    import store from '../../store/store'
    export default Vue.extend ({
        name: 'LeftSideBar',
        components: {
            
        },
        data() {
            return {
                miniVariant: false,
                clipped: false,
                scrollSettings: {
                    maxScrollbarLength: 160
                },
                menus: [] as any,
                tongDonChuaNhan:0 as any,
                lstNhomMxh: [] as any,
                searchParamsDonDatHang: { includeEntities: true, rowsPerPage: 0, tinhTrang: 1 } as DonDatHangApiSearchParams,
                user: this.$store.state.user.User
            }
        },
        created() {
            this.getMenu();
            //this.getSoDonChuaNhan(0);
            //this.$eventBus.$on('UpdateSoDonChuaNhan', this.getSoDonChuaNhan);

        },
        computed: {

            showLeftSideBar() :boolean {
                return this.$store.state.app.showLeftSideBar
            },
        },
        methods: {
            toggleSidebar(val: any) {
                if (val !== this.$store.state.app.showLeftSideBar) {
                    store.commit(MUTATION_TYPES.TOOGLE_LEFT_SIDE_BAR)
                }
            },
            getSoDonChuaNhan(so : number) {
                DonDatHangApi.search(this.searchParamsDonDatHang).then(res => {
                    this.tongDonChuaNhan = res.pagination.totalItems;
                });
            },
            getMenu() {
                this.menus = [{
                    title: 'Trang chủ',
                    icon: 'home',
                    link: '/',
                    show: true,//this.user!==null? true : false,
                    items: [
                        { title: 'Sản phẩm', link: '/danh-muc-san-pham', show: true }, //this.user !== null ? true : false },
                        { title: 'Dịch vụ', link: '/dich-vu', show: true },// this.user !== null ? true : false },
                        { title: 'Đơn hàng', link: '/danh-sach-don-hang', show:true },// this.user !== null ? true : false },
                        { title: 'Dịch vụ đã đặt', link: '/dich-vu-cua-toi', show: true },//this.user !== null ? true : false }
                    ] 
                },
                    {
                        title: 'Quản lý kinh doanh',
                        icon: 'account_circle',
                        link: '/',
                        show: true,//this.user !== null && this.user.LoaiTaiKhoanId !== 1? true: false,
                        items: [
                            { title: 'Quản lý dịch vụ', link: '/ql-dich-vu', show:true },// (this.user !== null && this.user.LoaiTaiKhoanId === 2 || this.user.LoaiTaiKhoanId === 4) ? true : false, },
                            { title: 'Quản lý danh mục', link: '/ql-danh-muc', show: true },//(this.user !== null && this.user.LoaiTaiKhoanId === 2 || this.user.LoaiTaiKhoanId === 4) ? true : false, },
                            { title: 'Quản lý sản phẩm', link: '/ql-san-pham', show: true },//(this.user !== null && this.user.LoaiTaiKhoanId === 2 || this.user.LoaiTaiKhoanId === 4) ? true : false, },
                            { title: 'Quản lý nhà cung cấp', link: '/ql-nha-cung-cap', show: true },//(this.user !== null && this.user.LoaiTaiKhoanId === 2 || this.user.LoaiTaiKhoanId === 4) ? true : false, },
                            { title: 'Khách đặt dịch vụ', link: '/khach-dat-dich-vu', show: true },//(this.user !== null && this.user.LoaiTaiKhoanId === 2 ? true : false)},
                            { title: 'Xử lý đơn hàng', link: '/xu-ly-don-hang', action: 'add', show: true },//(this.user != null && this.user.LoaiTaiKhoanId === 2 || this.user.LoaiTaiKhoanId === 3) ? true : false},
                            //{ title: 'Hủy hàng/ Thanh lý', link: '/thanh-ly-huy-hang'},
                        ]
                    },
                    {
                        title: 'Báo cáo thống kê',
                        icon: 'list_alt',
                        link: '/',
                        show: (this.user !== null && this.user.LoaiTaiKhoanId === 2 ? true : false),
                        items: [
                            { title: 'Mẫu 01 & 05', link: 'ds-mau-1', show: (this.user !== null && this.user.LoaiTaiKhoanId === 2 ? true : false) },
                            { title: 'Mẫu 02 & 04', link: 'ds-mau-2', show: (this.user !== null && this.user.LoaiTaiKhoanId === 2 ? true : false) },
                            { title: 'Mẫu 03', link: 'ds-mau-3', show: (this.user !== null && this.user.LoaiTaiKhoanId === 2 ? true : false) },

                        ]
                    },
]
            }
        }
    })
</script>

<style>
    #ex4 .p1[data-count]:after {
        position: absolute;
        right: 5%;
        top: 28%;
        content: attr(data-count);
        font-size: 12px;
        padding: .2em;
        border-radius: 50%;
        line-height: 1em;
        color: white;
        background: rgba(255,0,0,.85);
        text-align: center;
        min-width: 1.5em;
    }


    #leftSideBar .v-list__group__header .v-list__group__header__prepend-icon,
    #leftSideBar .v-list__tile__action {
        min-width: 26px;
    }

    #leftSideBar .v-list__group__header .v-list__group__header__prepend-icon,
    #leftSideBar .v-list__tile__action {
        padding: 0 5px;
    }

    #leftSideBar .v-list__group__items .v-list__tile__title {
        padding: 0 10px 0 0;
    }

    #leftSideBar .v-list__tile {
        padding: 0;
    }

    #leftSideBar .v-list__group__header .v-list__group__header__append-icon {
        padding: 0 5px;
    }

    #leftSideBar .v-list__group__header .v-list__group__header__append-icon,
    #leftSideBar .v-list__group__header .v-list__group__header__prepend-icon {
        padding: 0 5px !important;
    }

    #header-logo .v-toolbar__content {
        padding: 0 !important;
    }
    .v-list--dense {
        padding-top: 0;
        padding-bottom: 4px;
        margin-top: -5px;
    }
</style>