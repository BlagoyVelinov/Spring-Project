package bg.softuni.mycinematicketsapp.web;

import bg.softuni.mycinematicketsapp.config.SecurityUserDetails;
import bg.softuni.mycinematicketsapp.constants.Constant;
import bg.softuni.mycinematicketsapp.constants.ConstantTest;
import bg.softuni.mycinematicketsapp.models.dtos.OrderMovieDto;
import bg.softuni.mycinematicketsapp.models.dtos.view.UserViewDto;
import bg.softuni.mycinematicketsapp.models.entities.UserEntity;
import bg.softuni.mycinematicketsapp.models.entities.UserRole;
import bg.softuni.mycinematicketsapp.models.enums.CityName;
import bg.softuni.mycinematicketsapp.models.enums.UserRoleEnum;
import bg.softuni.mycinematicketsapp.models.enums.UserStatus;
import bg.softuni.mycinematicketsapp.services.OrderService;
import bg.softuni.mycinematicketsapp.services.SecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @MockBean
    private SecurityService securityService;

    private static SecurityUserDetails testUser;

    @BeforeEach
    void setUp() {
        UserEntity testUserEntity = new UserEntity();
        testUserEntity.setId(2L);
        testUserEntity.setUsername(ConstantTest.TEST_USERNAME);
        testUserEntity.setPassword("password");
        testUserEntity.setStatus(UserStatus.ACTIVE);
        testUserEntity.setRoles(List.of(new UserRole(UserRoleEnum.USER)));

        testUser = new SecurityUserDetails(testUserEntity);

        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Test
    void testCreateOrder_Success() throws Exception {
        OrderMovieDto orderDto = new OrderMovieDto();
        orderDto.setId(1L);
        orderDto.setOrderNumber("ORD123");
        orderDto.setUser(getUserView());
        orderDto.setLocation(CityName.SOFIA);
        orderDto.setProjectionDate(LocalDate.now().plusDays(1));

        when(orderService.createUserOrder(any(OrderMovieDto.class), eq(testUser.getUsername())))
                .thenReturn(orderDto);

        OrderMovieDto createOrder = new OrderMovieDto();
        createOrder.setMovieId(1L);
        createOrder.setBookingTimeId(1L);
        createOrder.setUser(getUserView());
        createOrder.setProjectionDate(LocalDate.now().plusDays(1));
        createOrder.setLocation(CityName.SOFIA);

        mockMvc.perform(post("/api/order")
                .with(user(testUser))
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createOrder)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(Constant.SUCCESS_CREATED_ORDER))
                .andExpect(jsonPath("$.order.id").value(1));
    }

    @Test
    void testGetOrderById_Success() throws Exception {
        UserViewDto userDto = getUserView();

        OrderMovieDto orderDto = new OrderMovieDto();
        orderDto.setId(1L);
        orderDto.setUser(userDto);

        when(orderService.getOrderDtoById(1L)).thenReturn(orderDto);

        doNothing().when(securityService).validateCurrentUser(eq(2L), any(Authentication.class));

        mockMvc.perform(get("/api/order/1")
                        .with(user(testUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(orderDto.getId()))
                .andExpect(jsonPath("$.user.id").value(orderDto.getUser().getId()));

        verify(orderService, times(1)).getOrderDtoById(1L);
        verify(securityService, times(1)).validateCurrentUser(eq(2L), any(Authentication.class));
    }

    @Test
    void testGetOrderById_Forbidden() throws Exception {
        UserViewDto userDto = getUserView();

        OrderMovieDto orderDto = new OrderMovieDto();
        orderDto.setId(1L);
        orderDto.setUser(userDto);

        when(orderService.getOrderDtoById(1L)).thenReturn(orderDto);

        doThrow(new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied"))
                .when(securityService).validateCurrentUser(eq(2L), any(Authentication.class));

        mockMvc.perform(get("/api/order/1")
                        .with(user(testUser))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());

        verify(orderService, times(1)).getOrderDtoById(1L);
        verify(securityService, times(1)).validateCurrentUser(eq(userDto.getId()), any(Authentication.class));
    }

    private UserViewDto getUserView() {
        UserViewDto userDto = new UserViewDto();
        userDto.setId(2L);
        userDto.setUsername(ConstantTest.TEST_USERNAME);
        userDto.setEmail(ConstantTest.USER_EMAIL);

        return userDto;
    }
}
