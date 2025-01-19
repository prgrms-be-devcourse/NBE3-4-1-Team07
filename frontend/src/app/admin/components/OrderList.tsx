import React from 'react';
import { Button } from '@mui/material';
import { Order } from '@/app/types/Order';

interface Filters
 {
    searchType: string;
    keyword: string;
    dateFrom: string;
    dateTo: string;
}

interface OrderListProps {
    filters: Filters;
    orders: Order[];
    selectAll: boolean;
    selectedOrders: Set<number>;
    handleFilterChange: (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => void;
    handleDelivery: () => void;
    handleSelectAllChange: (isChecked: boolean) => void;
    handleCheckboxChange: (id: number, isChecked: boolean) => void;
    handleOrderDetailModalOpen: (orderId: number) => void;
}

const OrderList: React.FC<OrderListProps> = ({
                                                 filters,
                                                 orders,
                                                 selectAll,
                                                 selectedOrders,
                                                 handleFilterChange,
                                                 handleDelivery,
                                                 handleSelectAllChange,
                                                 handleCheckboxChange,
                                                 handleOrderDetailModalOpen,
                                             }) => {

    return (
        <div className="flex-grow overflow-auto">
            <h1 className="text-xl font-bold mb-4">주문 목록</h1>

            {/* Filters */}
            <div className="flex items-center space-x-2 mb-4">
                <select
                    name="searchType"
                    value={filters.searchType}
                    onChange={handleFilterChange}
                    className="border rounded px-2 py-1"
                >
                    <option value="아이디">이메일</option>
                </select>
                <input
                    type="text"
                    name="keyword"
                    value={filters.keyword}
                    onChange={handleFilterChange}
                    placeholder="검색어"
                    className="border rounded px-2 py-1"
                />

                <input
                    type="date"
                    name="dateFrom"
                    value={filters.dateFrom}
                    onChange={handleFilterChange}
                    className="border rounded px-2 py-1"
                />
                <span>~</span>
                <input
                    type="date"
                    name="dateTo"
                    value={filters.dateTo}
                    onChange={handleFilterChange}
                    className="border rounded px-2 py-1"
                />
                <button className="bg-gray-800 text-white px-4 py-2 rounded">조회</button>
            </div>

            <Button onClick={handleDelivery}>배송</Button>

            {/* Order Table */}
            <div>
                <table className="w-full border-collapse border border-gray-300">
                    <thead>
                    <tr className="bg-gray-100">
                        <th className="border px-4 py-2">
                            <input
                                type="checkbox"
                                checked={selectAll}
                                onChange={(e) => handleSelectAllChange(e.target.checked)}
                            />
                        </th>
                        <th className="border px-4 py-2">주문번호</th>
                        <th className="border px-4 py-2">이메일</th>
                        <th className="border px-4 py-2">주문일자</th>
                        <th className="border px-4 py-2">주소</th>
                        <th className="border px-4 py-2">우편번호</th>
                        <th className="border px-4 py-2">총 금액</th>
                        <th className="border px-4 py-2">상태</th>
                        <th className="border px-4 py-2">주문상세</th>
                    </tr>
                    </thead>
                    <tbody>
                    {orders.map((order) => (
                        <tr key={order.id} className="text-center">
                            <td className="border px-4 py-2">
                                <input
                                    type="checkbox"
                                    checked={selectedOrders.has(order.id)}
                                    onChange={(e) => handleCheckboxChange(order.id, e.target.checked)}
                                />
                            </td>
                            <td className="border px-4 py-2">{order.id}</td>
                            <td className="border px-4 py-2">{order.email}</td>
                            <td className="border px-4 py-2">{order.orderDate}</td>
                            <td className="border px-4 py-2">{order.address}</td>
                            <td className="border px-4 py-2">{order.postalCode}</td>
                            <td className="border px-4 py-2">{order.totalPrice}</td>
                            <td className="border px-4 py-2">{order.state}</td>
                            <td className="border px-4 py-2">
                                <button
                                    className="bg-blue-500 text-white px-4 py-1 rounded"
                                    onClick={() => handleOrderDetailModalOpen(order.id)}
                                >
                                    정보
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default OrderList;
