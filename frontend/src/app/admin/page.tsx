"use client";

import React, { useEffect, useState, ChangeEvent } from "react";
import {Order, OrderDetailResponseDto} from "../types/Order";
import { Sidebar, Menu, MenuItem } from 'react-pro-sidebar';
import FormatListBulletedOutlinedIcon from '@mui/icons-material/FormatListBulletedOutlined';
import {Product, ProductRequestDto, ProductResponseDto} from "@/app/types/Product";
import OrderDetailModal from "@/app/admin/components/OrderDetailModal";
import ProductDetailModal from "@/app/admin/components/ProductDetailModal";
import ProductList from "@/app/admin/components/ProductList";
import OrderList from "@/app/admin/components/OrderList";
import ProductAddModal from "@/app/admin/components/ProductAddModal";

const getOrderList = async () => {
  const res = await fetch("/api/admin/orderList");
  const data = await res.json();
  return data;
};

const getProductList = async () => {
  const res = await fetch("/api/main/productList");

  if (!res.ok) {
    throw new Error("상품 목록 조회 실패");
  }

  const data = await res.json();
  return data;
};

export default function admin() {
  const [activeMenu, setActiveMenu] = useState("orderList");
  const [filters, setFilters] = useState({
        searchType: "",
        keyword: "",
        dateFrom: "",
        dateTo: "",
    });
  const [orders, setOrders] = useState<Order[]>([]);
  const [products, setProducts] = useState<Product[]>([]);

  const [isProductDetailModalOpen, setIsProductDetailModalOpen] = useState(false);
  const [isProductAddModalOpen, setIsProductAddModalOpen] = useState(false);
  const [isOrderDetailModalOpen, setIsOrderDetailModalOpen] = useState(false);


  const [formValues, setFormValues] = useState<Product | null>(null);
  const [orderDetail, setOrderDetail] = useState<OrderDetailResponseDto | null>(null);
  const [selectedOrders, setSelectedOrders] = useState<Set<number>>(new Set());
  const [selectAll, setSelectAll] = useState(false);


  useEffect(() => {
    // 데이터 fetching
    const fetchData = async () => {
      try {
        if(activeMenu == "orderList"){
          const data = await getOrderList();
          setOrders(data);
        } else if(activeMenu == "productList"){
          const data: ProductResponseDto = await getProductList();
          setProducts(data.products);
        }
      } catch (err) {
        console.log("데이터를 가져오는 데 문제가 발생했습니다.");
      }
    };

    fetchData();
  }, [activeMenu]);

  const fetchOrders = async () => {
        try {
            const res = await fetch("/api/admin/orderList");
            if (!res.ok) {
                throw new Error("주문 목록 조회 실패");
            }
            const data = await res.json();
            setOrders(data);
        } catch (error) {
            console.error("주문 목록 조회 에러:", error);
            alert("주문 목록 조회 중 문제가 발생했습니다.");
        }
    };

  const handleMenuClick = (menu: string) => setActiveMenu(menu);
  const handleFilterChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFilters({ ...filters, [name]: value });
  };

  const handleProductDetailModalOpen = async (id: number) => {
    try{
      const response: Response = await fetch(`/api/admin/product/${id}`);

      if(!response.ok){
        throw new Error("상품 정보를 불러오는데 실패했습니다.");
      }

      const data: Product = await response.json();

      setFormValues(data);
      setIsProductDetailModalOpen(true);
      return data;
    } catch(error){
      console.error(error);
      return null;
    }
  };
  const handleProductDetailModalClose = () => {
    setIsProductDetailModalOpen(false);
    setFormValues(null);
  };
  const handleProductSave = async () => {
        try {
            if (formValues?.id === 0) {
                // 새로운 상품 추가
                const response = await fetch("/api/admin/product", {
                    method: "POST",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify(formValues),
                });

                if (!response.ok) {
                    throw new Error("상품 추가에 실패했습니다.");
                }
            } else {
                // 기존 상품 수정
                const response = await fetch(`/api/admin/product/${formValues?.id}`, {
                    method: "PUT",
                    headers: {"Content-Type": "application/json"},
                    body: JSON.stringify({
                        name: formValues.name,
                        price: formValues.price,
                        quantity: formValues.quantity,
                    }),
                });

                if (!response.ok) {
                    throw new Error("상품 수정에 실패했습니다.");
                }
            }

            // 상품 목록 갱신
            const data: ProductResponseDto = await getProductList();
            setProducts(data.products);

            alert("저장되었습니다.");
            handleProductDetailModalClose();
        } catch (error) {
            console.error("상품 저장 오류 : " + error);
        }
    };

    const handleProductDelete = async (id: number) => {
        try{
            const response = await fetch(`api/admin/product/${id}`, {
                method: "DELETE",
            });


            if(!response.ok){
                throw new Error("상품 삭제 실패.");
            }

            const data: ProductResponseDto = await getProductList();
            setProducts(data.products);
            return data;
        } catch(error){
            console.error(error);
            return null;
        }
    }

    const handleProductCreate = async (productRequestDto: ProductRequestDto) => {
        try {

            const formData = new FormData();

            if (productRequestDto.image) {
                formData.append("image", productRequestDto.image);
            }

            formData.append("name", productRequestDto.name);
            formData.append("price", productRequestDto.price.toString());
            formData.append("quantity", productRequestDto.quantity.toString());
            formData.append("description", productRequestDto.description || "");

            const response = await fetch("/api/admin/product/create", {
                method: "POST",
                body: formData,
            });

            if (!response.ok) {
                throw new Error("상품 추가에 실패했습니다.");
            }

            // 상품 목록 갱신
            const data: ProductResponseDto = await getProductList();
            setProducts(data.products);
            alert("상품이 추가되었습니다.");
            handleProductAddModalClose();
        } catch (error) {
            console.error("상품 추가 오류 : " + error);
        }
    };

  const handleProductAddModalOpen = () => {
      setFormValues(null);
      setIsProductAddModalOpen(true);
  }
  const handleProductAddModalClose = () => {
      setFormValues(null);
      setIsProductAddModalOpen(false);
  }
  const handleOrderDetailModalOpen = async (id: number) => {
        try {
            const response: Response = await fetch(`/api/admin/order/${id}`);
            if (!response.ok) {
                throw new Error(`주문 상세 정보 조회 실패: ${response.statusText}`);
            }
            const data: OrderDetailResponseDto = await response.json();
            setOrderDetail(data);
            setIsOrderDetailModalOpen(true);
        } catch (error) {
            console.error("주문 상세 정보 조회 실패", error);
        }
    };
  const handleOrderDetailModalClose = () => {
      setIsOrderDetailModalOpen(false);
      setOrderDetail(null);
  }

  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormValues((prev) => ({
            ...prev!,
            [name]: name === 'price' || name === 'quantity' ? parseInt(value) : value,
        }));
    };

  const handleCheckboxChange = (orderId: number, isChecked: boolean) => {
      const order = orders.find(order => order.id === orderId);
      if (order && order.state === 'SHIPPED') {
          return; // SHIPPED 상태인 주문은 변경되지 않음
      }

      setSelectedOrders((prevSelectedOrders) => {
            const updatedSelectedOrders = new Set(prevSelectedOrders);
            if (isChecked) {
                updatedSelectedOrders.add(orderId);
            } else {
                updatedSelectedOrders.delete(orderId);
            }
            setSelectAll(updatedSelectedOrders.size === orders.length);
            return updatedSelectedOrders;
        });
    };
  const handleSelectAllChange = (isChecked: boolean) => {
      setSelectAll(isChecked);

      // SHIPPED 상태가 아닌 주문만 선택하기
      const allOrderIds = orders
          .filter(order => order.state !== 'SHIPPED')
          .map(order => order.id);

      if (isChecked) {
          setSelectedOrders(new Set(allOrderIds));
      } else {
          setSelectedOrders(new Set());
      }
    };

  const handleDelivery = async () => {
        try {
            const selectedOrderIds = Array.from(selectedOrders);
            const orderDeliveryDto = { id: selectedOrderIds };

            if (selectedOrderIds.length === 0) {
                alert("선택된 주문이 없습니다.");
                return;
            }

            const res = await fetch("/api/admin/delivery", {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify(orderDeliveryDto),
            });
            alert("배송 처리 성공");
            setSelectedOrders(new Set());
            setSelectAll(false);
            await fetchOrders();
        } catch (error) {
            alert("배송 처리 중 문제가 발생했습니다.");
        }
    };

  return (
      <div className="flex h-screen">
        <Sidebar backgroundColor="#FBFBFB" className="min-h-full">
          <Menu>
            <MenuItem
                onClick={() => handleMenuClick("orderList")}
                icon={<FormatListBulletedOutlinedIcon/>}>주문목록조회</MenuItem>
            <MenuItem
                onClick={() => handleMenuClick("productList")}>등록상품조회</MenuItem>
          </Menu>
        </Sidebar>
        <div className="w-3/4 p-4 flex-grow overflow-auto">
          {activeMenu === "orderList" && (
              <OrderList
                  orders={orders}
                  selectedOrders={selectedOrders}
                  selectAll={selectAll}
                  handleSelectAllChange={handleSelectAllChange}
                  handleCheckboxChange={handleCheckboxChange}
                  handleDelivery={handleDelivery}
                  handleFilterChange={handleFilterChange}
                  handleOrderDetailModalOpen={handleOrderDetailModalOpen}
                  filters={filters}
              />)}
          {activeMenu === "productList" && (
              <ProductList
                  products={products}
                  onAddProductClick={handleProductAddModalOpen}
                  onProductDetailClick={handleProductDetailModalOpen}
                  onProductDeleteClick={handleProductDelete}
              />)}
            <ProductAddModal
                open={isProductAddModalOpen}
                onClose={handleProductAddModalClose}
                onSave={handleProductCreate}
            />
          <ProductDetailModal
              open={isProductDetailModalOpen}
              onClose={handleProductDetailModalClose}
              formValues={formValues}
              onSave={handleProductSave}
              onInputChange={handleInputChange}
          />
          <OrderDetailModal
              open={isOrderDetailModalOpen}
              onClose={handleOrderDetailModalClose}
              orderDetail={orderDetail}
          />
        </div>
      </div>
  );
}
