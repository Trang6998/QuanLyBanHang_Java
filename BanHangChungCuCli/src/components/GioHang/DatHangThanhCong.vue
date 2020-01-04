<template>
    <v-dialog v-model="dialog" max-width="700">
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="white--text" style="height: 40px" v-bind:style="{ background: activeColor} ">
                    <h3>Đơn hàng {{donHang.soHieuDon}}</h3>
                    <v-spacer></v-spacer>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text pa-0>
                    <v-layout row wrap>
                        <v-flex xs12>
                            <h3>Bạn đã đặt mua thành công! Xin cảm ơn!</h3>
                        </v-flex>
                        <v-flex xs12>
                            <v-data-table :headers="tableHeader"
                                          :items="dsChiTietDonDatHang"
                                          :loading="loadingTable" hide-actions
                                          class="elevation-1" style="border-collapse: unset; background-color: unset!important">
                                <template slot="items" slot-scope="props">
                                    <td style="width: 40% ;padding-left: 0px!important; white-space: nowrap" class="text-nowrap">
                                        <v-layout nowrap style="min-width: 200px">
                                            <v-flex sm4>
                                                <v-img v-if="props.item.anhSanPham != null" slot="activator"
                                                       :src="APIS.HOST + 'fileupload/download?key=' + props.item.anhSanPham"
                                                       style="max-width: 100%;" id="img"
                                                       aspect-ratio="1"
                                                       class="grey lighten-2">
                                                    <template v-slot:placeholder>
                                                        <v-layout fill-height
                                                                  align-center
                                                                  justify-center
                                                                  ma-0>
                                                            <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                                        </v-layout>
                                                    </template>
                                                </v-img>
                                                <v-img v-else src='/static/images/photo-3x4.jpg' slot="activator"
                                                       style="max-width: 100%;" id="img"
                                                       aspect-ratio="1"
                                                       class="grey lighten-2">
                                                    <template v-slot:placeholder>
                                                        <v-layout fill-height
                                                                  align-center
                                                                  justify-center
                                                                  ma-0>
                                                            <v-progress-circular indeterminate color="grey lighten-5"></v-progress-circular>
                                                        </v-layout>
                                                    </template>
                                                </v-img>
                                            </v-flex>
                                            <v-flex sm8>
                                                <div>{{ props.item.sanPham ? props.item.sanPham.tenSanPham : "" }}</div>
                                            </v-flex>
                                        </v-layout>
                                    </td>
                                    <td class="text-xs-center">{{ props.item.giaXuat  | currency('', 0,{ thousandsSeparator: '.' })}}</td>
                                    <td class="text-xs-center">{{ props.item.soLuong }}</td>
                                    <td class="text-xs-center">{{ props.item.thanhTien  | currency('', 0,{ thousandsSeparator: '.' })}}</td>
                                 
                                </template>
                                <template slot="no-data">
                                    <p class="text-xs-center mb-0">Không có dữ liệu</p>
                                </template>
                            </v-data-table>
                        </v-flex>
                        <v-flex xs12>
                            <v-layout nowrap style="font-size: 16px;">
                                <v-spacer></v-spacer>
                                <b>Tổng tiền: </b>&nbsp;<span style="color: chocolate">{{donHang.tongTien  | currency('', 0,{ thousandsSeparator: '.' })}}</span>&nbsp;
                            </v-layout>
                        </v-flex>
                        <v-flex xs12 v-if="donHang.tinhTrang != 0">
                            <div>Ngày đặt: {{donHang.ngayDat | moment('DD/MM/YYYY hh:mm')}}</div>
                        </v-flex>
                    </v-layout>
                    <v-layout row wrap>
                        <v-flex xs12>
                            <v-layout row wrap>
                                <v-flex xs12>
                                    Thời gian hẹn lấy:
                                    <span v-if="donHang.henLayTu != null && donHang.henLayDen != null">
                                        <span v-if="soSanhNgay(donHang.henLayTu, donHang.henLayDen)">
                                            {{ donHang.henLayTu === null ? "" : donHang.henLayTu|moment('HH:mm') }} -
                                            {{ donHang.henLayDen === null ? "" : donHang.henLayDen|moment('HH:mm') }}
                                            {{ donHang.henLayTu === null ? "" : donHang.henLayTu|moment('DD/MM/YY') }}
                                        </span>
                                        <span v-else>
                                            {{donHang.henLayTu |moment("hh:mm DD/MM/YYYY")}} -
                                            {{donHang.henLayDen |moment("hh:mm DD/MM/YYYY")}}
                                        </span>

                                    </span>
                                    <span v-else-if="donHang.henLayTu != null && donHang.henLayDen == null">
                                        Sau {{donHang.henLayTu |moment("hh:mm DD/MM/YYYY")}}
                                    </span>
                                    <span v-else-if="donHang.henLayTu == null && donHang.henLayDen != null">
                                        Trước {{donHang.henLayDen |moment("hh:mm DD/MM/YYYY")}}
                                    </span>
                                    <span v-else>
                                        Mọi thời gian
                                    </span>
                                </v-flex>

                            </v-layout>
                        </v-flex>
                        <v-flex xs12>
                            <div>Ghi chú: {{donHang.ghiChu? donHang.ghiChu : 'Không'}}</div>
                        </v-flex>
                    </v-layout>
                </v-card-text>
            </v-card>
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import ChiTietDonDatHangApi, { ChiTietDonDatHangApiSearchParams } from '@/apiResources/ChiTietDonDatHangApi';
    import { ChiTietDonDatHang } from '@/models/ChiTietDonDatHang';
    import { DonDatHang } from '@/models/DonDatHang';
    import DonDatHangApi from '@/apiResources/DonDatHangApi';
    import APIS from '@/apisServer';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                activeColor: '#e46c0a',
                isUpdate: false,
                loading: false,
                dialog: false,
                donDatHang: {} as DonDatHang,
                dsChiTietDonDatHang: [] as ChiTietDonDatHang[],
                tableHeader: [
                    { text: 'Sản phẩm', value: '#', align: 'left', sortable: false },
                    { text: 'Đơn giá', value: 'donGia', align: 'center', sortable: false },
                    { text: 'Số lượng', value: 'soLuong', align: 'center', sortable: false },
                    { text: 'Số tiền', value: 'soTien', align: 'center', sortable: false },
                ],
                searchParamsChiTietDonDatHang: { includeEntities: true, rowsPerPage: 0 } as ChiTietDonDatHangApiSearchParams,
                loadingTable: false,
                selectedChiTietDonDatHang: {} as ChiTietDonDatHang,
                dialogConfirmDelete: false,
                APIS: APIS,
                daNhanDon: false,
                donHang: {} as DonDatHang,
                laTre: false,
                lyDo: '',
                trangThaiDon: -1,
                thoiGianHenToiThieu: null as any
            }
        },
        mounted() {
        },
        methods: {
            show(id: number) {
                this.dialog = true;
                this.searchParamsChiTietDonDatHang.donDatHangID = id;
                this.getDataFromApi(this.searchParamsChiTietDonDatHang);
                DonDatHangApi.detail(id).then(res => {
                    this.donHang = res;
                    this.trangThaiDon = res.tinhTrang;
                })
            },
            hide() {
                this.dialog = false;
            },
            soSanhNgay(tuNgay: Date, denNgay: Date) {
                if (tuNgay.toString().slice(0, 10) == denNgay.toString().slice(0, 10)) {
                    return true;
                }
                else return false;
            },
            getDataFromApi(searchParamsChiTietDonDatHang: ChiTietDonDatHangApiSearchParams): void {
                this.loadingTable = true;
                ChiTietDonDatHangApi.search(searchParamsChiTietDonDatHang).then(res => {
                    this.dsChiTietDonDatHang = res as any;
                    //this.searchParamsChiTietDonDatHang.totalItems = res.pagination.totalItems;
                    this.loadingTable = false;
                });
            },
        }
    });
</script>
<style>
</style>

