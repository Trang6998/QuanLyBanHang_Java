import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { SanPhamNhaCungCap } from '@/models/SanPhamNhaCungCap'
export interface SanPhamNhaCungCapApiSearchParams extends Pagination {
    nhaCungCapID?:number;
    sanPhamID?:number;
    nhanVienID?:number;
}
class SanPhamNhaCungCapApi extends BaseApi {
    search(searchParams: SanPhamNhaCungCapApiSearchParams): Promise<PaginatedResponse<SanPhamNhaCungCap>> {

        return new Promise<PaginatedResponse<SanPhamNhaCungCap>>((resolve: any, reject: any) => {
            HTTP.get('sanphamnhacungcap', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<SanPhamNhaCungCap> {
        return new Promise<SanPhamNhaCungCap>((resolve: any, reject: any) => {
            HTTP.get('sanphamnhacungcap/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, sanPhamNhaCungCap: SanPhamNhaCungCap): Promise<SanPhamNhaCungCap> {
        return new Promise<SanPhamNhaCungCap>((resolve: any, reject: any) => {
            HTTP.put('sanphamnhacungcap/' + id, 
                sanPhamNhaCungCap
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(sanPhamNhaCungCap: SanPhamNhaCungCap): Promise<SanPhamNhaCungCap> {
        return new Promise<SanPhamNhaCungCap>((resolve: any, reject: any) => {
            HTTP.post('sanphamnhacungcap', 
                sanPhamNhaCungCap
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<SanPhamNhaCungCap> {
        return new Promise<SanPhamNhaCungCap>((resolve: any, reject: any) => {
            HTTP.delete('sanphamnhacungcap/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new SanPhamNhaCungCapApi();
