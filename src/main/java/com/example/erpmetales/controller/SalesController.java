package com.example.erpmetales.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.erpmetales.ErpmetalesApplication;
import com.example.erpmetales.dao.SalesDao;
import com.example.erpmetales.model.Customer;
import com.example.erpmetales.model.Provider;
import com.example.erpmetales.model.Order;
import com.example.erpmetales.model.OrderDetail;
import com.example.erpmetales.model.Product;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/sales")
public class SalesController {

    private final ErpmetalesApplication erpmetalesApplication;

    @Autowired
    private SalesDao salesDao;

    SalesController(ErpmetalesApplication erpmetalesApplication) {
        this.erpmetalesApplication = erpmetalesApplication;
    }

    // VISTAS
    // ---------------------------------------------------------------------------------

    @GetMapping("")
    public String showSalesPage(Model model) {
        List<Product> products = salesDao.getAllProducts();
        List<OrderDetail> orders = salesDao.getAllOrders();
        List<Customer> customers = salesDao.getAllCustomers();

        model.addAttribute("lista_products", products);
        model.addAttribute("lista_ordenes", orders);
        model.addAttribute("lista_clientes", customers);
        return "sales/sales";
    }

    @GetMapping("/customers")
    public String getCustomers(Model model) {
        List<Customer> customers = salesDao.getAllCustomers();
        model.addAttribute("lista_clientes", customers);
        return "sales/customers";
    }

    @GetMapping("/suppliers")
    public String getSuppliers(Model model) {
        List<Provider> providers = salesDao.getAllSuppliers();
        model.addAttribute("lista_proovedores", providers);
        return "sales/suppliers";
    }

    @GetMapping("/reports")
    public String getReports(Model model) {
        List<Customer> customers = salesDao.getAllCustomers();
        model.addAttribute("lista_clientes", customers);
        return "sales/reports";
    }

    @GetMapping("orders")
    public String listOrders(Model model) {
        List<OrderDetail> orders = salesDao.getAllOrders();
        model.addAttribute("orders", orders);
        return "sales/orders";
    }

    @GetMapping("/customers/search")
    @ResponseBody
    public List<Map<String, String>> searchCustomers(@RequestParam String query) {
        List<Customer> customers = salesDao.searchCustomers(query);
        return customers.stream().map(c -> Map.of("name", c.getFirst_name() + " " + c.getLast_name())).toList();
    }

    /*
     * @GetMapping("/products/search")
     * 
     * @ResponseBody
     * public List<Map<String, String>> searchProducts(@RequestParam String query) {
     * List<Product> products = salesDao.searchProducts(query);
     * return products.stream().map(p -> Map.of("name", p.getNombre())).toList();
     * }
     */

    // CLIENTES
    // ---------------------------------------------------------------------------------

    @GetMapping("/customers/new")
    public String showNewCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "sales/new-customer";
    }

    // Agregar un cliente
    @PostMapping("/customers/save")
    public String saveCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        int result = salesDao.addCustomer(customer);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Client added successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding client.");
        }
        return "redirect:/sales/customers";
    }

    @GetMapping("/customers/edit/{id}")
    public String showEditCustomerForm(@PathVariable int id, Model model) {
        Customer customer = salesDao.getCustomerById(id);
        if (customer == null) {
            return "redirect:/sales/customers";
        }
        model.addAttribute("customer", customer);
        return "sales/edit-customer";
    }

    // Actualizar un cliente
    @PostMapping("/customers/edit/{id}")
    public String updateCustomer(@PathVariable int id, @ModelAttribute Customer customer,
            RedirectAttributes redirectAttributes) {
        customer.setId(id);
        int result = salesDao.updateCustomer(customer);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Client update successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error update client.");
        }
        return "redirect:/sales/customers";
    }

    @PostMapping("/customers/delete")
    public String deleteCustomer(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        int result = salesDao.deleteCustomer(id);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Client delete successfully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error delete client.");
        }
        return "redirect:/sales/customers";
    }

    // PROOVEDORES
    // ---------------------------------------------------------------------------------

    @GetMapping("/suppliers/new")
    public String showNewProviderForm(Model model) {
        model.addAttribute("customer", new Provider());
        return "sales/new-provider";
    }

    // Agregar un proovedor
    @PostMapping("/suppliers/save")
    public String saveProvider(@ModelAttribute Provider provider, RedirectAttributes redirectAttributes) {
        int result = salesDao.addProvider(provider);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Provider added successfully..");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding provider.");
        }
        return "redirect:/sales/suppliers";
    }

    // Editar un proovedor
    @GetMapping("/suppliers/edit/{id}")
    public String showEditProviderForm(@PathVariable int id, Model model) {
        Provider provider = salesDao.getProviderById(id);
        if (provider == null) {
            return "redirect:/sales/suppliers";
        }
        model.addAttribute("provider", provider);
        return "sales/edit-provider";
    }

    // Actualizar un proovedor
    @PostMapping("/suppliers/edit/{id}")
    public String updateProvider(@PathVariable int id, @ModelAttribute Provider provider,
            RedirectAttributes redirectAttributes) {
        provider.setId(id);
        int result = salesDao.updateProvider(provider);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Properly updated supplier.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to update the provider.");
        }
        return "redirect:/sales/suppliers";
    }

    // Eliminar un proovedor
    @PostMapping("/suppliers/delete")
    public String deleteProvider(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        int result = salesDao.deleteProvider(id);

        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Properly delete supplier.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to delete the provider.");
        }
        return "redirect:/sales/suppliers";
    }

    // ORDENES
    // ---------------------------------------------------------------------------------
    // Agregar Orden
    @GetMapping("/order/new")
    public String showNewOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "sales";
    }

    // Agregar una Orden
    @PostMapping("/order/save")
    public String saveOrder(@ModelAttribute Order order, RedirectAttributes redirectAttributes) {

        Product product = salesDao.getDetallesProductos(order.getProductId());
        if (product.getStock() < order.getAmount()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Insufficient stock for the order.");
            return "redirect:/sales";
        }

        int result = salesDao.addOrder(order);
        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Order added correctly.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error adding order.");
        }
        return "redirect:/sales";
    }

    // Editar una Orden
    @GetMapping("/order/edit/{id}")
    public String showEditOrderForm(@PathVariable int id, Model model) {
        OrderDetail orden = salesDao.getOrderById(id);
        if (orden == null) {
            return "redirect:/sales";
        }

        List<Product> products = salesDao.getAllProducts();
        List<Customer> customers = salesDao.getAllCustomers();
        List<OrderDetail> orders = salesDao.getAllOrders();

        model.addAttribute("lista_products", products);
        model.addAttribute("lista_clientes", customers);

        model.addAttribute("order", orden);

        model.addAttribute("provider", orden);
        return "sales/edit-order";
    }

    // Actualizar una orden
    @PostMapping("/order/edit/{id}")
    public String updateOrder(@PathVariable int id,
            @ModelAttribute OrderDetail orderDetail,
            @RequestParam int customerId,
            @RequestParam int productId,
            @RequestParam("orderDate") String orderDateStr,
            RedirectAttributes redirectAttributes) {

        LocalDate localDate = LocalDate.parse(orderDateStr);
        LocalDateTime orderDate = localDate.atStartOfDay();
        orderDetail.setOrderDate(orderDate);

        orderDetail.setCustomerId(customerId);
        orderDetail.setProductId(productId);

        int result = salesDao.updateOrder(orderDetail);

        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Order update successufully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error to update the order.");
        }
        return "redirect:/sales";
    }

    // Eliminar una orden
    @PostMapping("/order/delete")
    public String deleteOrder(@RequestParam("id") int id, RedirectAttributes redirectAttributes) {
        int result = salesDao.deleteOrder(id);

        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Order delete successufully.");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error to delete the order.");
        }
        return "redirect:/sales";
    }

    // Productos
    @GetMapping("/product/details")
    @ResponseBody
    public Map<String, Object> getProductDetails(@RequestParam("id") int productId) {
        Product product = salesDao.getDetallesProductos(productId);

        Map<String, Object> response = new HashMap<>();
        response.put("precio", product.getPrice());
        response.put("stock", product.getStock());
        return response;
    }

}
