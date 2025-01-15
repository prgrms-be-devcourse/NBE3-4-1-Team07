import Image from "next/image";

export default function Home() {
  return (
    <div className="container-fluid p-4">
      <div className="flex justify-center mb-4">
        <h1 className="text-center">Grids & Circle</h1>
      </div>
      
      <div className="max-w-[950px] w-[90%] mx-auto bg-white rounded-2xl shadow-lg">
        <div className="flex flex-col md:flex-row">
          {/* 상품 목록 섹션 */}
          <div className="md:w-2/3 mt-4 flex flex-col items-start p-3 pt-0">
            <h5 className="font-bold">상품 목록</h5>
            <ul className="w-full space-y-2 mt-3">
              {[1, 2, 3].map((item) => (
                <li key={item} className="flex items-center gap-4 p-3 border rounded-lg">
                  <div className="w-20">
                    <Image
                      src="https://i.imgur.com/HKOFQYa.jpeg"
                      alt="커피"
                      width={100}
                      height={100}
                      className="w-full"
                    />
                  </div>
                  <div className="flex-1">
                    <div className="text-gray-500">커피콩</div>
                    <div>Columbia Nariñó</div>
                  </div>
                  <div className="text-center">5000원</div>
                  <div>
                    <button className="px-4 py-2 border border-gray-800 rounded-md hover:bg-gray-100">
                      추가
                    </button>
                  </div>
                </li>
              ))}
            </ul>
          </div>

          {/* Summary 섹션 */}
          <div className="md:w-1/3 bg-gray-200 p-4 rounded-r-2xl">
            <div>
              <h5 className="font-bold">Summary</h5>
            </div>
            <hr className="my-4" />
            
            <div className="space-y-2">
              <h6 className="flex items-center">
                Columbia Nariñó 
                <span className="ml-2 px-2 py-1 bg-gray-800 text-white text-sm rounded">2개</span>
              </h6>
              <h6 className="flex items-center">
                Brazil Serra Do Caparaó
                <span className="ml-2 px-2 py-1 bg-gray-800 text-white text-sm rounded">2개</span>
              </h6>
              <h6 className="flex items-center">
                Columbia Nariñó
                <span className="ml-2 px-2 py-1 bg-gray-800 text-white text-sm rounded">2개</span>
              </h6>
            </div>

            <form className="mt-4 space-y-4">
              <div>
                <label htmlFor="email" className="block mb-1">이메일</label>
                <input
                  type="email"
                  id="email"
                  className="w-full p-2 border rounded"
                />
              </div>
              <div>
                <label htmlFor="address" className="block mb-1">주소</label>
                <input
                  type="text"
                  id="address"
                  className="w-full p-2 border rounded"
                />
              </div>
              <div>
                <label htmlFor="postcode" className="block mb-1">우편번호</label>
                <input
                  type="text"
                  id="postcode"
                  className="w-full p-2 border rounded"
                />
              </div>
              <div className="text-sm">
                당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
              </div>
            </form>

            <div className="flex justify-between items-center border-t mt-4 pt-4">
              <h5>총금액</h5>
              <h5>15000원</h5>
            </div>
            
            <button className="w-full bg-gray-800 text-white py-2 rounded-md mt-4 hover:bg-gray-700">
              결제하기
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
