import { Product } from "@/app/types/Product";
import { Button } from "@mui/material";
import Image from "next/image";

interface ProductListProps {
    products: Product[];
    onProductDetailClick: (id: number) => void;
    onAddProductClick: () => void;
}

const ProductList: React.FC<ProductListProps> = ({
                                                     products,
                                                     onProductDetailClick,
                                                     onAddProductClick,
                                                 }) => (
    <div>
        <h1 className="text-xl font-bold mb-4">상품 목록</h1>
        <Button onClick={onAddProductClick}>추가</Button>
        <ul className="w-full space-y-2 mt-3">
            {products.map((product) => (
                <li key={product.id} className="flex justify-between items-center p-3 border rounded-lg hover:bg-gray-50">
                    <div className="flex items-center gap-4">
                        <div className="w-20 h-20 relative">
                            <Image src={`http://localhost:8080${product.imgPath}`}
                                   alt={product.name}
                                   fill
                                   className="object-cover rounded-lg"
                                   sizes="(max-width: 768px) 80px, 80px" />

                        </div>
                        <div>
                            <h6 className="font-semibold">{product.name}</h6>
                            <p className="text-sm text-gray-600">{product.description}</p>
                            <p className="text-blue-600 font-medium">{product.price.toLocaleString()}원</p>
                        </div>
                    </div>
                    <button
                        className="px-4 py-2 bg-gray-800 text-white rounded hover:bg-gray-700 transition"
                        onClick={() => onProductDetailClick(product.id)}
                    >
                        상세
                    </button>
                </li>
            ))}
        </ul>
    </div>
);

export default ProductList;
