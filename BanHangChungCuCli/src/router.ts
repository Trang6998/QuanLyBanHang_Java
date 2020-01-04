import Vue from 'vue';
import Router, { Route } from 'vue-router';
import store from './store/store';
import { state } from './store/state';
import { HTTP } from '@/http-servicesNew';
import Home from './components/Home.vue';
import ErrorPage from './components/Login/ErrorPage.vue';
import Login from './components/Login/Login.vue';
import DanhMucSanPham from './components/SanPham/DanhMucSanPham.vue';
import DanhSachDanhMuc from './components/QuanLyKinhDoanh/QuanLyDanhMuc/DanhSachDanhMuc.vue';
import DanhSachDichVu from './components/QuanLyKinhDoanh/QuanLyDichVu/DanhSachDichVu.vue';
import DanhSachSanPham from './components/QuanLyKinhDoanh/QuanLySanPham/DanhSachSanPham.vue';
import DanhSachNhaCungCap from './components/QuanLyKinhDoanh/QuanLyNhaCungCap/DanhSachNhaCungCap.vue';
import KhachDatDichVu from './components/QuanLyKinhDoanh/KhachDatDichVu/KhachDatDichVu.vue';
import XuLyDonHang from './components/QuanLyKinhDoanh/XuLyDonHang/XuLyDonHang.vue';
//kien
import DonHang from './components/DonHang/DonHang.vue';
import ChiTietDichVu from './components/DichVu/ChiTietDichVu.vue';
import DichVuCuaToi from './components/DatDichVu/DichVuCuaToi.vue';
import SanPham from './components/SanPham/DanhSachSanPham.vue';
import ChiTietSanPham from './components/SanPham/ChiTietSanPham.vue';
import dsDichVu from './components/DichVu/dsDichVu.vue';
import GioHang from './components/GioHang/GioHang.vue';

// tung
import Mau01 from './components/BaoCaoThongKe/Mau01.vue';
import Mau02 from './components/BaoCaoThongKe/Mau02.vue';
import Mau03 from './components/BaoCaoThongKe/Mau03.vue';
Vue.use(Router);
export default new Router({
    routes: [
        {
            path: '/',
            name: 'Home',
            component: Home,
            //beforeEnter: guardRoute
        },
        {
            path: '/error-page',
            name: 'error-page',
            //component: ErrorPage
        },
        {
            path: '/login',
            name: 'login',
            component: Login
        },
        // Mua h�ng
        {
            path: '/ql-danh-muc',
            name: 'DanhSachDanhMuc',
            component: DanhSachDanhMuc  ,
            //beforeEnter: guardRoute

        },
        {
            path: '/ql-dich-vu',
            name: 'DanhSachDichVu',
            component: DanhSachDichVu  ,
            //beforeEnter: guardRoute

        },
        {
            path: '/ql-san-pham',
            name: 'DanhSachSanPham',
            component: DanhSachSanPham  ,
            //beforeEnter: guardRoute

        },
        {
            path: '/ql-nha-cung-cap',
            name: 'DanhSachNhaCungCap',
            component: DanhSachNhaCungCap  ,
            //beforeEnter: guardRoute

        },
        {
            path: '/khach-dat-dich-vu',
            name: 'KhachDatDichVu',
            component: KhachDatDichVu  ,
            //beforeEnter: guardRoute

        },
        {
            path: '/xu-ly-don-hang',
            name: 'XuLyDonHang',
            component: XuLyDonHang  ,
            //beforeEnter: guardRoute

        },
		{
            path: '/chi-tiet-san-pham/:id',
            name: 'ChiTietSanPham',
            component: ChiTietSanPham,
            //beforeEnter: guardRoute

        },
        // mua hang
        {
            path: '/danh-muc-san-pham',
            name: 'DanhMucSanPham',
            component: DanhMucSanPham,
            //beforeEnter: guardRoute

        },
        {
            path: '/danh-muc-san-pham/:id',
            name: 'DanhSachSanPham',
            component: SanPham,
            //beforeEnter: guardRoute

        },
        {
            path: '/',
            name: 'Home',
            component: Home,
            //beforeEnter: guardRoute

        },
        {
            path: '/chi-tiet-san-pham/:id',
            name: 'ChiTietSanPham',
            component: ChiTietSanPham,
            //beforeEnter: guardRoute

        },
        {
            path: '/gio-hang',
            name: 'GioHang',
            component: GioHang,
            //beforeEnter: guardRoute

        },
        {
            path: '/dich-vu',
            name: 'dsDichVu',
            component: dsDichVu,
            //beforeEnter: guardRoute
        },
        {
            path: '/danh-sach-don-hang',
            name: 'DonHang',
            component: DonHang,
            //beforeEnter: guardRoute
        },
        {
            path: '/dich-vu-cua-toi',
            name: 'DichVuCuaToi',
            component: DichVuCuaToi,
            //beforeEnter: guardRoute
        },
        {
            path: '/dich-vu/:id',
            name: 'ChiTietDichVu',
            component: ChiTietDichVu,
            //beforeEnter: guardRoute
        },
        {
            path: '/ds-mau-1',
            name: 'Mau01',
            component: Mau01,
            //beforeEnter: guardRoute
        },
        {
            path: '/ds-mau-2',
            name: 'Mau02',
            component: Mau02,
            //beforeEnter: guardRoute
        },
        {
            path: '/ds-mau-3',
            name: 'Mau03',
            component: Mau03,
            //beforeEnter: guardRoute
        },
    ]
});

function guardRoute(to: Route, from: Route, next: any): void {
    const isAuthenticated = state.user && state.user.Token ? state.user.Token.IsAuthenticated : false;
    if (!isAuthenticated) {
        next({
            path: '/login',
            query: {
                redirect: to.fullPath
            }
        });
    } else {
        HTTP.get('/auth/validatetoken')
            .then(response => {
                next();
            })
            .catch(e => {
                store.commit('CLEAR_ALL_DATA');
                next({
                    path: '/login',
                    query: {
                        redirect: to.fullPath
                    }
                });
            });
    }
}