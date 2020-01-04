
import {
    TOOGLE_LEFT_SIDE_BAR,
    TOOGLE_RIGHT_SIDE_BAR
} from './MUTATION_TYPES'

export default {
    
    updateTitle (state:any, text:any) {
        state.app.title = text
    },
    UPDATE_APP(state: any, app: any) {
        state.app = app
    },
    UPDATE_AUTH(state: any, auth: any) {
        state.auth = auth
    },

    UPDATE_USER(state: any, user: any) {
        state.user.User = user;
        state.user.Token.IsAuthenticated = true;
        if (state.user.loaiUser === 1) {
            state.user.HoGiaDinh = true
        } else if (state.user.loaiUser === 2) {
            state.user.QuanTriVien = true
        } 
    },

    [TOOGLE_LEFT_SIDE_BAR](state: any) {
        state.app.showLeftSideBar = !state.app.showLeftSideBar
    },

    [TOOGLE_RIGHT_SIDE_BAR](state: any) {
        state.app.showRightSideBar = !state.app.showRightSideBar
    },


    CLEAR_ALL_DATA(state: any) {
        state.user = {
            Token: {
                IsAuthenticated: false,
                Token: null,
                UserName: null,
                RefreshToken: null,
                EffectiveTime: null,
                ExpiresTime: null
            },
            Profile: {
                Username: null,
                FullName: null,
            },
            User: {
                HoGiaDinhId: null,
                TenChuHo: '',
                ToaNha: '',
                Phong: '',
                SoDienThoai: null,
                ToaNhaId: null,
                UserId: null,
                PhongId: null,
                LoaiTaiKhoanId: null,
                AnhDaiDien: null
            }
        }
    }
}
