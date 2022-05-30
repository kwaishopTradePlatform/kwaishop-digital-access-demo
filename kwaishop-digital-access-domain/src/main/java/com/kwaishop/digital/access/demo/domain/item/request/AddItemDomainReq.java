package com.kwaishop.digital.access.demo.domain.item.request;

import java.util.List;

import com.kwaishop.digital.access.demo.domain.item.model.AddSkuDTO;

import lombok.Data;

/**
 * @author zhangyiying
 * Created on 2022-05-18
 */
@Data
public class AddItemDomainReq {
    /**
     * 商品名称
     */
    private String title;

    /**
     * 外部商品id，仅供记录外部商品和快手商品对应关系； 注意：外部商品id不可与历史新建商品重复
     */
    private Number relItemId;

    /**
     * 类目id
     */
    private Number categoryId;

    /**
     * 类目名称
     */
    private String categoryName;

    /**
     * 商品主图，1. 最多上传9张图片 2. 单张图片大小不超过2M，支持png/jpeg/webp/bmp格式
     */
    private String[] imageUrls;

    /**
     * sku信息列表。0 < 数量 <= 600
     */
    private List<AddSkuDTO> skuList;

    /**
     * 商品关联的视频id
     */
    private Number itemVideoId;

    /**
     * 是否限购，默认不限购
     */
    private Boolean purchaseLimit;

    /**
     * 商品限购数量。purchaseLimit为true时，限购数量必填
     */
    private Number limitCount;

    /**
     * 商品类目属性，商品类目属性有必填项，必传。（注意：类目属性不允许重复）
     */
    private AddItemPropValue[] itemPropValues;

    /**
     * 商品详情描述。0 < 长度 <= 1000
     */
    private String details;

    /**
     * 是否分仓，默认为false
     */
    private Boolean stockPartner;

    /**
     * 商品备注最多8个字符 备注支持中文、英文、数字
     */
    private String itemRemark;

    /**
     * 服务规则
     */
    private ServiceRule serviceRule;

    /**
     * 运费模板id
     */
    private Long expressTemplateId;

    /**
     * 是否定点开售，默认为false
     */
    private Boolean saleTimeFlag;

    /**
     * 定点开售时间，如果开启定点开售必填
     */
    private Long timeOfSale;

    /**
     * 支付方式：1 货到付款 2 在线支付 3 在线支付和货到付款；默认2 目前OpenAPI端只支持「在线支付」方式
     */
    private Integer payWay;

    /**
     * 是否是多仓库存模式，默认是false
     */
    private Boolean multipleStock;

    /**
     * 门店ID
     */
    private List<Long> poiIds;

    /**
     * 商品白底图，请上传格式为png/jpeg/jpg的图片，长宽比为1:1的图片，像素480*480px以上的图片，大小小于2M
     */
    private String whiteBaseImageUrl;

    /**
     * 商品透明图，请上传格式为png的图片，长宽比为1:1的图片，像素480*480px以上的图片，大小小于2M
     */
    private String transparentImageUrl;

    /**
     * 商品短标题长度必须大于4且小于20个字符（2-10汉字）
     */
    private String shortTitle;

    /**
     * 商品卖点长度必须大于8且小于24个字符（4-12汉字）
     */
    private String sellingPoint;

    /**
     * 使用说明,非必传，约定字符限制400个中文字长度以内。空行数量10行以内。
     */
    private String instructions;
}
