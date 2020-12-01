package test.unit.controller;

import controller.CustomerController;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@Tag("UnitTest")
@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {
    @InjectMocks
    private CustomerController customerController;

}
