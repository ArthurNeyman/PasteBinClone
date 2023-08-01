package com.paste_bin_clone.tests;

import com.paste_bin_clone.config.DatabaseSetupExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(DatabaseSetupExtension.class)
public class PastControllerTest {


}
