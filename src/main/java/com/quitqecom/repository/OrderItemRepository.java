package com.quitqecom.repository;

import com.quitqecom.dto.OrderItemDtoV2;
import com.quitqecom.dto.TopProductDto;
import com.quitqecom.model.Order;
import com.quitqecom.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    @Query("""
        select oi
        from OrderItem oi
        where oi.order.id=?1
        """)
    List<OrderItem> getAllOrderItemsByOrder(int orderId);

    @Query("""
        select new com.quitqecom.dto.OrderItemDtoV2(
        p.productName,
        p.price,
        oi.quantity,
        oi.priceAtPurchase,
        o.id,
        o.status)
        from OrderItem oi
        join oi.product p
        join oi.order o
        where o.id=?1
        """)
    List<OrderItemDtoV2> getOrderDetails(int orderId);

    @Query("""
        select oi.order
        from OrderItem oi
        where oi.product.id=?1
        """)
    List<Order> getOrdersByProduct(int productId);

    @Query("""
select oi
from OrderItem oi
where oi.product.seller.id=?1
""")
    List<OrderItem> getSellerOrderItems(int sellerId);

    @Query("""
select coalesce(
sum(oi.priceAtPurchase * oi.quantity),
0
)
from OrderItem oi
where oi.product.seller.id=?1
and oi.order.status =
com.quitqecom.enums.OrderStatus.PLACED
""")
    Double calculateRevenue(int sellerId);


    @Query("""
select coalesce(
sum(oi.priceAtPurchase * oi.quantity),
0
)
from OrderItem oi
where oi.order.id=?1
""")
    Double calculateOrderAmount(int orderId);

    @Query("""
select count(distinct oi.order.id)
from OrderItem oi
where oi.product.seller.id=?1
""")
    Long countSellerOrders(int sellerId);


    @Query("""
select new com.quitqecom.dto.TopProductDto(
p.productName,
sum(oi.quantity)
)
from OrderItem oi
join oi.product p
where p.seller.id=?1
and oi.order.status=
com.quitqecom.enums.OrderStatus.PLACED
group by p.productName
order by sum(oi.quantity) desc
""")
    List<TopProductDto> topProducts(int sellerId);

    @Query("""
select count(distinct oi.order.id)
from OrderItem oi
where oi.product.seller.id=?1
and oi.order.status=
com.quitqecom.enums.OrderStatus.PLACED
""")
    Long countPlacedOrders(int sellerId);

    @Query("""
select count(distinct oi.order.id)
from OrderItem oi
where oi.product.seller.id=?1
and oi.order.status=
com.quitqecom.enums.OrderStatus.CANCELLED
""")
    Long countCancelledOrders(int sellerId);

    @Query("""
select coalesce(
sum(oi.quantity),
0
)
from OrderItem oi
where oi.product.seller.id=?1
and oi.order.status=
com.quitqecom.enums.OrderStatus.PLACED
""")
    Long totalProductsSold(int sellerId);

    @Query("""
select coalesce(
sum(oi.priceAtPurchase * oi.quantity),
0
)
from OrderItem oi
where oi.product.seller.id=?1
and oi.order.status=
com.quitqecom.enums.OrderStatus.PLACED
and year(oi.order.createdAt)=?2
and month(oi.order.createdAt)=?3
""")
    Double monthlyRevenue(
            int sellerId,
            int year,
            int month
    );


    @Query("""
select
month(oi.order.createdAt),
coalesce(
sum(oi.priceAtPurchase * oi.quantity),
0
)
from OrderItem oi
where oi.product.seller.id=?1
and oi.order.status=
com.quitqecom.enums.OrderStatus.PLACED
group by month(oi.order.createdAt)
order by month(oi.order.createdAt)
""")
    List<Object[]> monthlyRevenueChart(
            int sellerId
    );

    @Query("""
select
quarter(oi.order.createdAt),
coalesce(
sum(oi.priceAtPurchase * oi.quantity),
0
)
from OrderItem oi
where oi.product.seller.id=?1
and oi.order.status=
com.quitqecom.enums.OrderStatus.PLACED
group by quarter(oi.order.createdAt)
order by quarter(oi.order.createdAt)
""")
    List<Object[]> quarterlyRevenue(
            int sellerId
    );

    @Query("""
select count(oi)
from OrderItem oi
where oi.order.customer.id=?1
and oi.product.id=?2
""")
    Long hasPurchased(
            int customerId,
            int productId
    );
}
