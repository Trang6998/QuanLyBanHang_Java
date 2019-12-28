import { HTTP } from '@/HTTPServices'
import { BaseApi } from './BaseApi'
import { PaginatedResponse,Pagination } from './PaginatedResponse'
import { Media } from '@/models/Media'
export interface MediaApiSearchParams extends Pagination {
    dichVuID?:number;
    sanPhamID?:number;
}
class MediaApi extends BaseApi {
    search(searchParams: MediaApiSearchParams): Promise<PaginatedResponse<Media>> {

        return new Promise<PaginatedResponse<Media>>((resolve: any, reject: any) => {
            HTTP.get('media', {
                params: searchParams
            }).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    detail(id: number): Promise<Media> {
        return new Promise<Media>((resolve: any, reject: any) => {
            HTTP.get('media/' + id).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    update(id: number, media: Media): Promise<Media> {
        return new Promise<Media>((resolve: any, reject: any) => {
            HTTP.put('media/' + id, 
                media
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    insert(media: Media): Promise<Media> {
        return new Promise<Media>((resolve: any, reject: any) => {
            HTTP.post('media', 
                media
            ).then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
    delete(id: number): Promise<Media> {
        return new Promise<Media>((resolve: any, reject: any) => {
            HTTP.delete('media/' + id)
            .then((response) => {
                resolve(response.data);
            }).catch((error) => {
                reject(error);
            })
        });
    }
}
export default new MediaApi();
