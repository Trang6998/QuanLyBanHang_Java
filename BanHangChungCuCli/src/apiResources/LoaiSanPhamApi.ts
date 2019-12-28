import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { LoaiSanPham } from '@/models/LoaiSanPham'
export interface LoaiSanPhamApiSearchParams extends Pagination {
    loaiSanPhamPID?: number;
    loaiSanPhamID?: number;
    tenLoaiSanPham?: string;
    laDanhMucCha?: boolean;
}
class LoaiSanPhamApi extends BaseApi {
    search(searchParams: LoaiSanPhamApiSearchParams): Promise<PaginatedResponse<LoaiSanPham>> {

        return new Promise<PaginatedResponse<LoaiSanPham>>((resolve: any, reject: any) => {
            HTTP.get('loaisanpham', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<LoaiSanPham> {
        return new Promise<LoaiSanPham>((resolve: any, reject: any) => {
            HTTP.get('loaisanpham/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, loaiSanPham: LoaiSanPham): Promise<LoaiSanPham> {
        return new Promise<LoaiSanPham>((resolve: any, reject: any) => {
            HTTP.put('loaisanpham/' + id, 
                loaiSanPham
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(loaiSanPham: LoaiSanPham): Promise<LoaiSanPham> {
        return new Promise<LoaiSanPham>((resolve: any, reject: any) => {
            HTTP.post('loaisanpham', 
                loaiSanPham
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<LoaiSanPham> {
        return new Promise<LoaiSanPham>((resolve: any, reject: any) => {
            HTTP.delete('loaisanpham/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new LoaiSanPhamApi();
