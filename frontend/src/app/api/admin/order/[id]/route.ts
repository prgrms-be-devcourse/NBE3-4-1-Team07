import {NextResponse} from "next/server";
import {OrderDetailResponseDto} from "@/app/types/Order";


// GET api/admin/order/${id}
async function fetchOrderDetail(id: number): Promise<OrderDetailResponseDto> {
    try {
        const response = await fetch(`http://localhost:8080/admin/order/${id}`);

        if (!response.ok) {
            throw new Error(`Failed to fetch order details for ID: ${id}. Status: ${response.status}`);
        }

        const rawData = await response.json();

        const products = rawData.map((item: any) => ({
            name: item.name,
            price: item.price,
            quantity: item.quantity,
            imgPath: item.imgPath,
        }));

        return { products };
    } catch (error) {
        console.error('Error fetching order detail:', error);
        throw error;
    }
}


export async function GET(
    request: Request,
    { params }: { params: { id: string } }
) {
    const { id } = await params;
    const orderDetail = await fetchOrderDetail(Number(id));
    return NextResponse.json(orderDetail);
}