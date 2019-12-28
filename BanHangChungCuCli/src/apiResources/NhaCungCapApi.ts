import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { NhaCungCap } from '@/models/NhaCungCap'
export interface NhaCungCapApiSearchParams extends Pagination {
    tenNhaCungCap?: string;
}
class NhaCungCapApi extends BaseApi {
    search(searchParams: NhaCungCapApiSearchParams): Promise<PaginatedResponse<NhaCungCap>> {

        return new Promise<PaginatedResponse<NhaCungCap>>((resolve: any, reject: any) => {
            HTTP.get('nhacungcap', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<NhaCungCap> {
        return new Promise<NhaCungCap>((resolve: any, reject: any) => {
            HTTP.get('nhacungcap/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, nhaCungCap: NhaCungCap): Promise<NhaCungCap> {
        return new Promise<NhaCungCap>((resolve: any, reject: any) => {
            HTTP.put('nhacungcap/' + id, 
                nhaCungCap
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(nhaCungCap: NhaCungCap): Promise<NhaCungCap> {
        return new Promise<NhaCungCap>((resolve: any, reject: any) => {
            HTTP.post('nhacungcap', 
                nhaCungCap
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<NhaCungCap> {
        return new Promise<NhaCungCap>((resolve: any, reject: any) => {
            HTTP.delete('nhacungcap/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new NhaCungCapApi();
