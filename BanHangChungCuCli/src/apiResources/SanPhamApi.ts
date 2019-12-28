import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { SanPham } from '@/models/SanPham'
export interface SanPhamApiSearchParams extends Pagination {
    loaiSanPhamID?:number;
    tenSanPham?: string;
    trangThaiAnHien?: boolean;
    toaNhaID?: number;
    layCaDanhMucCon?: boolean;
    toiThieu?: number;
    toiDa?: number;
}
export interface SanPhamThemVaoGioApiParams extends Pagination {
    sanPhamID?: number;
    soLuong?: number;
    giaXuat?: number;
    taiKhoanKhachHangID?: number;
}
class SanPhamApi extends BaseApi {
    search(searchParams: SanPhamApiSearchParams): Promise<PaginatedResponse<SanPham>> {
        return new Promise<PaginatedResponse<SanPham>>((resolve: any, reject: any) => {
            HTTP.get('sanpham', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<SanPham> {
        return new Promise<SanPham>((resolve: any, reject: any) => {
            HTTP.get('sanpham/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, sanPham: SanPham): Promise<SanPham> {
        return new Promise<SanPham>((resolve: any, reject: any) => {
            HTTP.put('sanpham/' + id, 
                sanPham
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(sanPham: SanPham): Promise<SanPham> {
        return new Promise<SanPham>((resolve: any, reject: any) => {
            HTTP.post('sanpham', 
                sanPham
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insertCart(params: SanPhamThemVaoGioApiParams): Promise<SanPham> {
        return new Promise<SanPham>((resolve: any, reject: any) => {
            HTTP.get('sanpham/themVaoGio', {
                params: params
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<SanPham> {
        return new Promise<SanPham>((resolve: any, reject: any) => {
            HTTP.delete('sanpham/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new SanPhamApi();
