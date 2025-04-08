package com.example.erpmetales.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.erpmetales.ErpmetalesApplication;
import com.example.erpmetales.dao.ProductionDao;
import com.example.erpmetales.dao.SalesDao;
import com.example.erpmetales.model.Customer;
import com.example.erpmetales.model.OrderDetail;
import com.example.erpmetales.model.Product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/production")
public class ProductionController {

    private final ErpmetalesApplication erpmetalesApplication;

    private ProductionDao productionDao;
    private SalesDao salesDao;

    @Autowired
    ProductionController(ErpmetalesApplication erpmetalesApplication, ProductionDao productionDao, SalesDao salesDao) {
        this.erpmetalesApplication = erpmetalesApplication;
        this.productionDao = productionDao;
        this.salesDao = salesDao;
    }

    // VISTAS
    // ------------------------------------------------------------------------

    @GetMapping("")
    public String showProductionPage(Model model) {
        List<OrderDetail> orders = productionDao.getAllOrdersPending();
        List<Customer> customers = salesDao.getAllCustomers();

        model.addAttribute("lista_ordenes_pendientes", orders);
        model.addAttribute("lista_clientes", customers);
        model.addAttribute("orden_cliente", null); // Inicialmente vacío

        return "production/production";
    }

    @PostMapping("/search-order/customer")
    public String buscarOrdenPorCliente(@RequestParam int customerId, Model model) {
        List<OrderDetail> orders = productionDao.getAllOrdersPending(); // Obtener todas por defecto
        List<OrderDetail> ordersByCustomer = productionDao.getCustomerOrder(customerId); // Obtener solo del cliente

        model.addAttribute("lista_ordenes_pendientes", orders);
        model.addAttribute("lista_clientes", salesDao.getAllCustomers());
        model.addAttribute("orden_cliente", ordersByCustomer); // Enviar las órdenes filtradas

        return "production/production";
    }

    @PostMapping("/order/send")
    public String sendOrder(@RequestParam("id") int orderId, RedirectAttributes redirectAttributes) {
        int result = productionDao.updateOrderStatus(orderId, "Pending");

        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Order sent to Quality successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error sending order.");
        }

        return "redirect:/production"; // Redirige a la página principal de producción
    }

    @GetMapping("/order/view/{id}")
    public String showViewOrderForm(@PathVariable int id, Model model) {
        OrderDetail order = salesDao.getOrderById(id);
        if (order == null) {
            return "redirect:/production";
        }

        // Obtener datos adicionales si la orden está rechazada
        if ("Rejected".equals(order.getStatus())) {
            OrderDetail rejectedDetails = productionDao.getOrderRejected(id);
            order.setDescription(rejectedDetails.getDescription());
            order.setDefective_parts(rejectedDetails.getDefective_parts());
        }

        List<Product> products = salesDao.getAllProducts();
        List<Customer> customers = salesDao.getAllCustomers();

        model.addAttribute("lista_products", products);
        model.addAttribute("lista_clientes", customers);
        model.addAttribute("order", order);

        return "production/view-order";
    }

}
