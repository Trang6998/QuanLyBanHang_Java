export const STORAGE_KEY = 'insight-client-system';
let Cookies = require('js-cookie');

let syncedData = {

    user: {
        Token: {
            IsAuthenticated: false,
        },
        User: {
            hoTen: '',
            email: '',
            diaChi: '',
            soDienThoai: null,
            userID: null,
            loaiUser: null,
        },
        HoGiaDinh: false,
        QuanTriVien: false,
        Phong: false
    }
}


const notSyncedData = {
    app: {
        showLeftSideBar: true,
        showRightSideBar: false,
        searchText: '',
        searchTimestamp: null,
        title: ''
    }
}
// Sync with local storage.
let userLogin = Cookies.get(STORAGE_KEY)
if (userLogin) {
    var data = JSON.parse(userLogin)
    if (data === undefined) {
        data = ''
    } else {
        syncedData = data
    }
}

// Merge data and export it.
export const state = Object.assign(syncedData, notSyncedData)
