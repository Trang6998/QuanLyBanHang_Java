<template>
    <v-dialog v-model="dialog" max-width="600" persistent>
        <v-container pa-0 grid-list-md>
            <v-card>
                <v-card-title class="primary white--text" style="height: 40px">
                    <h3>Tổng hợp đơn </h3>
                    <v-spacer></v-spacer>
                    <v-btn @click="print" small class="primary ma-0"><v-icon>print</v-icon>In</v-btn>
                    <v-btn class="white--text ma-0" small @click="hide" icon dark><v-icon>close</v-icon></v-btn>
                </v-card-title>
                <v-card-text id="print">
                    <v-layout row wrap>
                        <v-flex xs12>
                            <span>Thông số: <br /></span>
                            <span v-if="searchParamsTongHopDon.tuNgay != null && searchParamsTongHopDon.denNgay != null">
                                - Tổng hợp đơn từ {{ searchParamsTongHopDon.tuNgay === null ? "" : searchParamsTongHopDon.tuNgay |moment('DD/MM/YYYY HH:mm') }} đến {{ searchParamsTongHopDon.denNgay === null ? "" : searchParamsTongHopDon.denNgay |moment('DD/MM/YYYY HH:mm') }} <br />
                            </span>
                            <span v-else-if="searchParamsTongHopDon.tuNgay != null && searchParamsTongHopDon.denNgay == null">
                                - Tổng hợp đơn từ sau {{ searchParamsTongHopDon.tuNgay === null ? "" : searchParamsTongHopDon.tuNgay |moment('DD/MM/YYYY HH:mm') }}<br />
                            </span>
                            <span v-else-if="searchParamsTongHopDon.tuNgay == null && searchParamsTongHopDon.denNgay != null">
                                - Tổng hợp đơn đến trước {{ searchParamsTongHopDon.denNgay === null ? "" : searchParamsTongHopDon.denNgay |moment('DD/MM/YYYY HH:mm') }} <br />
                            </span>
                            <span>
                                - Trạng thái tổng hợp đơn: {{trangThaiDon === null ? '' : trangThaiDon}}                         <br />

                            </span>
                            
                        </v-flex>
                        <v-flex xs12>
                            <v-data-table :headers="tableHeader"
                                          :items="dsTongHopDon"
                                          hide-actions
                                          :loading="loadingTable"
                                          class="table-border table">
                                <template slot="items" slot-scope="props">
                                    <tr colspan="4"> 
                                        <td colspan="4">{{props.item.tenLoai}}</td>
                                    </tr>
                                    <tr v-for="(loaisp, i) in props.item.lstSanPham" :key="i">
                                        <td>{{i+1}}</td>
                                        <td>{{loaisp.tenSanPham}}</td>
                                        <td>{{loaisp.tongSanPham}}</td>
                                        <td>{{loaisp.tongSoDon}}</td>
                                    </tr>
                                   
                                </template>
                                <template slot="no-data">
                                    <p class="text-xs-center mb-0">Không có dữ liệu</p>
                                </template>
                            </v-data-table>
                        </v-flex>
                    </v-layout>
                </v-card-text>
            </v-card>
        </v-container>
    </v-dialog>
</template>

<script lang="ts">
    import { Vue } from 'vue-property-decorator';
    import DonDatHangApi, { TongHopDonApiSearchParams } from '@/apiResources/DonDatHangApi';
    import Printd from 'printd';

    export default Vue.extend({
        $_veeValidate: {
            validator: 'new'
        },
        components: {},
        data() {
            return {
                d: {} as any,
                cssText: `
                    span {
                        font-size: 18px!important;
                    }
                    .v-table__overflow {
                      width: 100%;
                      overflow-x: auto;
                      overflow-y: hidden;
                    }
                    table.v-table {
                      border-radius: 2px;
                      border-collapse: collapse;
                      border-spacing: 0;
                      width: 100%;
                      max-width: 100%;
                    }
                    table.v-table thead td:not(:nth-child(1)),
                    table.v-table tbody td:not(:nth-child(1)),
                    table.v-table thead th:not(:nth-child(1)),
                    table.v-table tbody th:not(:nth-child(1)),
                    table.v-table thead td:first-child,
                    table.v-table tbody td:first-child,
                    table.v-table thead th:first-child,
                    table.v-table tbody th:first-child {
                      padding: 0 24px;
                    }
                    table.v-table thead tr {
                      height: 56px;
                    }
                    table.v-table thead th {
                      font-weight: 500;
                      font-size: 18px;
                      transition: 0.3s cubic-bezier(0.25, 0.8, 0.5, 1);
                      white-space: nowrap;
                      -webkit-user-select: none;
                         -moz-user-select: none;
                          -ms-user-select: none;
                              user-select: none;
                    }
                    table.v-table thead th.sortable {
                      pointer-events: auto;
                    }
                    table.v-table thead th > div {
                      width: 100%;
                    }
                    table.v-table tbody tr {
                      transition: background 0.3s cubic-bezier(0.25, 0.8, 0.5, 1);
                      will-change: background;
                    }
                    table.v-table tbody td,
                    table.v-table tbody th {
                      height: 48px;
                    }
                    table.v-table tbody td {
                      font-weight: 400;
                      font-size: 18px;
                    }
                    table.v-table .input-group--selection-controls {
                      padding: 0;
                    }
                    table.v-table .input-group--selection-controls .input-group__details {
                      display: none;
                    }
                    table.v-table .input-group--selection-controls.checkbox .v-icon {
                      left: 50%;
                      -webkit-transform: translateX(-50%);
                              transform: translateX(-50%);
                    }
                    table.v-table .input-group--selection-controls.checkbox .input-group--selection-controls__ripple {
                      left: 50%;
                      -webkit-transform: translate(-50%, -50%);
                              transform: translate(-50%, -50%);
                    }
                    table.v-table tfoot tr {
                      height: 48px;
                    }
                    table.v-table tfoot tr td {
                      padding: 0 24px;
                    }
                    .v-datatable .v-input--selection-controls {
                      margin: 0;
                      padding: 0;
                    }
                    .v-datatable thead th.column.sortable {
                      cursor: pointer;
                      outline: 0;
                    }
                    .v-datatable thead th.column.sortable .v-icon {
                      font-size: 18px;
                      display: inline-block;
                      opacity: 0;
                      transition: 0.3s cubic-bezier(0.25, 0.8, 0.5, 1);
                    }
                    .v-datatable thead th.column.sortable:focus .v-icon,
                    .v-datatable thead th.column.sortable:hover .v-icon {
                      opacity: 0.6;
                    }
                    .v-datatable thead th.column.sortable.active {
                      -webkit-transform: none;
                              transform: none;
                    }
                    .v-datatable thead th.column.sortable.active .v-icon {
                      opacity: 1;
                    }
                    .v-datatable thead th.column.sortable.active.desc .v-icon {
                      -webkit-transform: rotate(-180deg);
                              transform: rotate(-180deg);
                    }
                    /** Actions */
                    .v-datatable__actions {
                      display: flex;
                      justify-content: flex-end;
                      align-items: center;
                      font-size: 18px;
                      flex-wrap: wrap-reverse;
                    }
                    .v-datatable__actions .v-btn {
                      color: inherit;
                    }
                    .v-datatable__actions .v-btn:last-of-type {
                      margin-left: 14px;
                    }
                    .v-datatable__actions__range-controls {
                      display: flex;
                      align-items: center;
                      min-height: 48px;
                    }
                    .v-datatable__actions__pagination {
                      display: block;
                      text-align: center;
                      margin: 0 32px 0 24px;
                    }
                    .v-datatable__actions__select {
                      display: flex;
                      align-items: center;
                      justify-content: flex-end;
                      margin-right: 14px;
                      white-space: nowrap;
                    }
                    .v-datatable__actions__select .v-select {
                      flex: 0 1 0;
                      margin: 13px 0 13px 34px;
                      padding: 0;
                      position: initial;
                    }
                    .v-datatable__actions__select .v-select__selections {
                      flex-wrap: nowrap;
                    }
                    .v-datatable__actions__select .v-select__selections .v-select__selection--comma {
                      font-size: 18px;
                    }
                    .v-datatable__progress {
                      height: auto !important;
                    }
                    .v-datatable__progress tr,
                    .v-datatable__progress td,
                    .v-datatable__progress th {
                      height: auto !important;
                    }
                    .v-datatable__progress th {
                      padding: 0 !important;
                    }
                    .v-datatable__progress th .v-progress-linear {
                      margin: 0;
                    }
                    .v-datatable__expand-row {
                      border: none !important;
                    }
                    .v-datatable__expand-col {
                      padding: 0 !important;
                      height: 0px !important;
                    }
                    .v-datatable__expand-col--expanded {
                      border-bottom: 1px solid rgba(0,0,0,0.12);
                    }
                    .v-datatable__expand-content {
                      transition: height 0.3s cubic-bezier(0.25, 0.8, 0.5, 1);
                    }
                    .v-datatable__expand-content > .card {
                      border-radius: 0;
                      box-shadow: none;
                    }

                    table.v-table thead th {
                        font-size: 18px !important;
                        font-weight: bold !important;
                        background-color: #fafafa !important;
                        opacity: 1;
                    }
                        table.v-table thead th.sortable {
                            color: #1976d2 !important;
                        }
                    table.v-table tbody td:first-child,
                    table.v-table tbody td:not(:first-child),
                    table.v-table thead td:first-child,
                    table.v-table thead td:not(:first-child) {
                        font-size: 18px !important;
                        height: 30px !important;
                    }
                    table.v-table tbody td:first-child, table.v-table tbody td:not(:first-child),
                    table.v-table tbody th:first-child, table.v-table tbody th:not(:first-child),
                    table.v-table thead td:first-child, table.v-table thead td:not(:first-child),
                    table.v-table thead th:first-child, table.v-table thead th:not(:first-child) {
                        padding: 0px 10px !important;
                    }
                    table.v-table tbody td:first-child,
                    table.v-table tbody td:not(:first-child),
                    table.v-table tbody th:first-child,
                    table.v-table tbody th:not(:first-child),
                    table.v-table thead td:first-child,
                    table.v-table thead td:not(:first-child),
                    table.v-table thead th:first-child,
                    table.v-table thead th:not(:first-child) {
                        padding-left: 10px !important;
                        padding-right: 10px !important;
                    }
                    table.v-table thead tr {
                        height: 35px !important;
                    }
                        table.v-table thead tr.v-datatable__progress {
                            height: 0px !important;
                        }
                    .table-border th, .table-border td {
                        border: 1px solid #ccc;
                    }

                    .table-border th, .table-border td {
                        border: 1px solid #ccc;
                    }
                    .table .btn--icon {
                        margin: 0;
                        width: 28px;
                        height: 28px;
                    }
                    table.v-table tbody td.v-datatable__expand-col {
                        padding: 0 !important;
                        height: 0 !important;
                    }
                `,
                dsTongHopDon: [] as any,
                dialog: false,
                isUpdate: false,
                tableHeader: [
                    { text: 'STT', value: 'ThuocTinhSanPhamID', align: 'center', sortable: false },
                    { text: 'Sản phẩm', value: 'SanPham.SanPhamID', align: 'center', sortable: false },
                    { text: 'Lượng đặt', value: 'NoiDungMoTa', align: 'center', sortable: false },
                    { text: 'Lượng đơn', value: '#', align: 'center', sortable: false },
                ],
                loadingTable: false,
              
                trangThaiDon: null as any,
                searchParamsTongHopDon: { includeEntities: true, rowsPerPage: 0 } as TongHopDonApiSearchParams,
            }
        },
        watch: {
        },
        mounted() {
        },
        computed: {
        },
        methods: {
            show(tuNgay: Date, denNgay: Date, trangThai: number) {
                this.dialog = true;
                this.searchParamsTongHopDon.tuNgay = tuNgay;
                this.searchParamsTongHopDon.denNgay = denNgay;
                this.searchParamsTongHopDon.tinhTrang = trangThai;

                if (trangThai === -1) // chuagui
                    this.trangThaiDon = 'Tất cả';
                if (trangThai === 0) // giohang
                    this.trangThaiDon = 'Giỏ hàng'
                if (trangThai === 1) // dagui
                    this.trangThaiDon = 'Chưa xử lý'
                if (trangThai === 2) // dangxuly
                    this.trangThaiDon = 'Đang xử lý'
                if (trangThai === 3) // chua thanh toan
                    this.trangThaiDon = 'Đã giao - chưa thanh toán'
                if (trangThai === 4) // done
                    this.trangThaiDon = 'Đã giao - đã thanh toán'
                this.getDataFromApi();
            },
            getDataFromApi(): void {
                DonDatHangApi.tonghopdon(this.searchParamsTongHopDon).then(res => {
                    this.dsTongHopDon = res.data;
                    this.searchParamsTongHopDon.totalItems = res.pagination.totalItems;
                });
            },
            print() {
                let printElement = document.getElementById('print')
                if (printElement) {
                    this.d = new Printd();
                    this.d.print(printElement, this.cssText)
                }
            },
            hide() {
                this.dialog = false;
            },
            
        }
    });
</script>
<style>
    .ql-editing {
        left: 0px !important;
        top: -10px !important;
    }

    .theme--light.v-pagination .v-pagination__item {
        display: none !important;
        background: #fff;
        color: #000;
        width: auto;
        min-width: 34px;
        padding: 0 5px;
    }

    .v-responsive {
        cursor: pointer !important;
        position: relative;
        overflow: hidden;
        -webkit-box-flex: 1;
        -ms-flex: 1 0 auto;
        flex: 1 0 auto;
        display: -webkit-box;
        display: -ms-flexbox;
        display: flex;
    }

    .buttondelete {
        display: none;
    }

    .wrapper:hover img {
        /* Change the filter in here */
    }

    .wrapper:hover .buttondelete {
        display: inline-block;
    }
</style>

