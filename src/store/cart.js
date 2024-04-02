import { defineStore } from 'pinia';
import { reactive } from 'vue';
import { ElMessage } from "element-plus";
import service from "../request/index.js";
export const useCartStore = defineStore('cartList', {
    state: () => ({
        cartList: JSON.parse(localStorage.getItem('cartList') || []),//获取购物车列表
        products: reactive([])
    }),
    actions: {
        async fetchProducts(ids) {
            for (let id of ids) {
                try {
                    const product = await service.get(`/mall/product/id=${id}`);
                    this.products.push(product.data);
                } catch (error) {
                    if (error.response && (error.response.status === 404)) {
                        ElMessage.error(`商品id ${id} 请求失败: ${error.response.status}`);
                    } else {
                        throw error;
                    }
                }
            }
        },
        updateProducts(cartList) {
            for (let item of cartList) {
                const product = this.products.find(p => p.id === item.productid);
                if (product) {
                    const attribution = product.attributions.find(a => a.product_id === item.productid && a.id === item.attrid);
                    if (attribution) {
                        attribution.num = item.num;
                    }
                }
            }
        },
        saveCartList() {
            // reduce遍历products数组，将产品的属性id、属性id和数量添加到cartList数组中。
            const cartList = this.products.reduce((acc, product) => {
                product.attributions.forEach(attribution => {
                    if (attribution.num > 0) {
                        acc.push({ productid: product.id, attrid: attribution.id, num: attribution.num });
                    }
                });
                return acc;
            }, []);
            localStorage.setItem('cartList', JSON.stringify(cartList));
        },
        addProduct(attrid) {
            this.products.find(item => {
                const attribution = item.attributions.find(a => a.id === attrid);
                if (attribution) {
                    attribution.num += 1;
                    return true;
                }
                return false;
            });
            this.saveCartList();
        },
        subtractProduct(attrid) {
            this.products.find(item => {
                const attribution = item.attributions.find(a => a.id === attrid);
                if (attribution && attribution.num > 1) {
                    attribution.num -= 1;
                    return true;
                }

                return false;
            });
            this.saveCartList();
        },
        deleteProduct(attrid) {
            this.products.find(item => {
                const attribution = item.attributions.find(a => a.id === attrid);
                if (attribution) {
                    attribution.num = 0;
                    return true;
                }
                return false;
            });
            this.saveCartList();
        },
    }
});
