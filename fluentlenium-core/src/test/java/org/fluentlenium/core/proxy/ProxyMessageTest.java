package org.fluentlenium.core.proxy;

import org.assertj.core.api.Assertions;
import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.hook.DefaultHookChainBuilder;
import org.fluentlenium.core.hook.HookChainBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProxyMessageTest {
    @Mock
    private WebElement element1;

    @Mock
    private WebElement element2;

    @Mock
    private WebElement element3;

    @Mock
    private ElementLocator locator;

    @Mock
    private FluentControl fluentControl;

    private HookChainBuilder hookChainBuilder;

    private ComponentInstantiator componentInstantiator;

    @Before
    public void before() {
        componentInstantiator = new DefaultComponentInstantiator(fluentControl);
        hookChainBuilder = new DefaultHookChainBuilder(fluentControl, componentInstantiator);
    }

    @Test
    public void testNoSuchElementMessage() {
        Mockito.when(locator.findElement()).thenReturn(element1);

        final WebElement proxy = LocatorProxies.createWebElement(locator);
        LocatorProxies.now(proxy);

        NoSuchElementException noSuchElementException = LocatorProxies.noSuchElement(proxy);

        Assertions.assertThat(noSuchElementException).hasMessageStartingWith("Element locator (Lazy Element) is not present");
    }

    @Test
    public void testNoSuchElementListMessage() {
        Mockito.when(locator.findElements()).thenReturn(Arrays.asList(element1, element2, element3));

        final List<WebElement> proxy = LocatorProxies.createWebElementList(locator);
        LocatorProxies.now(proxy);

        NoSuchElementException noSuchElementException = LocatorProxies.noSuchElement(proxy);

        Assertions.assertThat(noSuchElementException)
                .hasMessageStartingWith("Elements locator (Lazy Element List) is not present");
    }
}