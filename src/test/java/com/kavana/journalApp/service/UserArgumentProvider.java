package com.kavana.journalApp.service;

import com.kavana.journalApp.entity.User;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class UserArgumentProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
        return  Stream.of(
                Arguments.of(User.builder().userName("Ram1").password("Ram1").build()),
                Arguments.of(User.builder().userName("shyam1").password("shyam1").build())
        );
    }
}
