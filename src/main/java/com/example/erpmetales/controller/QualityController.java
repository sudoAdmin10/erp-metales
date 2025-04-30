package com.example.erpmetales.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.erpmetales.ErpmetalesApplication;
import com.example.erpmetales.dao.QualityDao;
import com.example.erpmetales.dao.ProductionDao;
import com.example.erpmetales.model.Customer;
import com.example.erpmetales.model.OrderDetail;
import com.example.erpmetales.service.UserRoleService;

import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/quality")
public class QualityController {
    private final ErpmetalesApplication erpmetalesApplication;

    private QualityDao qualityDao;
    private ProductionDao productionDao;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    QualityController(ErpmetalesApplication erpmetalesApplication, QualityDao qualityDao, ProductionDao productionDao) {
        this.erpmetalesApplication = erpmetalesApplication;
        this.qualityDao = qualityDao;
        this.productionDao = productionDao;
    }

    // VISTAS
    // ------------------------------------------------------------------------
    @GetMapping("")
    public String showqualityPage(Model model, Principal principal) {
        String userRole = userRoleService.getUserRole(principal);
        // Agregar el rol al modelo
        model.addAttribute("userRole", userRole);
        List<OrderDetail> orders = qualityDao.getAllOrdersPending();

        model.addAttribute("lista_ordenes_pendientes", orders);

        return "quality/quality";
    }

    @PostMapping("/order/reject")
    public String rechazarLote(@RequestParam("id") int id,
            @RequestParam("description") String description,
            @RequestParam("defective_parts") String defectiveParts,
            RedirectAttributes redirectAttributes,
            Principal principal,
            Model model) {
        String userRole = userRoleService.getUserRole(principal);
        // Agregar el rol al modelo
        model.addAttribute("userRole", userRole);

        // Actualizar la orden con la descripción y piezas defectuosas
        int result = qualityDao.rechazarLote(id, description, defectiveParts);

        // Cambiar el estado a "Rejected"
        int result2 = productionDao.updateOrderStatus(id, "Rejected");

        if (result > 0 && result2 > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Order rejected successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error rejecting order.");
        }

        return "redirect:/quality";
    }

    @PostMapping("/order/send")
    public String sendOrder(@RequestParam("id") int orderId, RedirectAttributes redirectAttributes, Principal principal,
            Model model) {
        String userRole = userRoleService.getUserRole(principal);
        // Agregar el rol al modelo
        model.addAttribute("userRole", userRole);
        int result = productionDao.updateOrderStatus(orderId, "Accepted");

        if (result > 0) {
            redirectAttributes.addFlashAttribute("successMessage", "Order sent to Quality successfully!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Error sending order.");
        }

        return "redirect:/production"; // Redirige a la página principal de producción
    }

}
