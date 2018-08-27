package com.spidermanteam.spiderpuppies.services.invoice;

import com.spidermanteam.spiderpuppies.SpiderPuppiesApplicationTests;
import com.spidermanteam.spiderpuppies.data.base.InvoiceRepository;
import com.spidermanteam.spiderpuppies.data.base.SubscriberRepository;
import com.spidermanteam.spiderpuppies.models.*;
import com.spidermanteam.spiderpuppies.services.InvoiceServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.rmi.NoSuchObjectException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InvoiceService_Tests{

    @Mock
    InvoiceRepository mockRepository;
    @Mock
    SubscriberRepository subscriberRepository;

    private InvoiceServiceImpl invoiceService;
    private Invoice invoice;

    @Before
    public void beforeTest() {
        Client client = new Client(new User("UserName", "Password", (byte) 1), "ClientFullName", "EIK");
        TelecomService telecomService = new TelecomService("Type", "SubsPlan", BigDecimal.valueOf(0.00), new ArrayList<>());
        LocalDate date = LocalDate.now();
        Subscriber subscriber = new Subscriber("SubscPhone", "FirstName", "LastName", "PIN", "Address", new ArrayList<>(), new ArrayList<>(), date, date, client, BigDecimal.valueOf(0.00));
        invoice = new Invoice(subscriber, telecomService, "BGN");
        invoice.setId(1);


        when(mockRepository.findById(1))
                .thenReturn(invoice);


        invoiceService = new InvoiceServiceImpl(mockRepository, subscriberRepository);
    }

    @Test
    public void findInvoiceById_whenInvoiceIsPresent_ShouldReturnInvoice() throws Exception {
        //Arrange
        Invoice actualInvoice = invoiceService.findInvoiceById(1);

        //Act
        verify(mockRepository)
                .findById(1);

        //Assert
        Assert.assertEquals(invoice.getId(), actualInvoice.getId());
    }


    @Test
    public void listAllInvoice_whenInvoiceListIsPresent_ShouldReturnInvoiceList() throws Exception {
        //Arrange
        List<Invoice> invoices = new ArrayList<>(3);
        invoices.add(new Invoice());
        invoices.add(new Invoice());
        invoices.add(new Invoice());

        //Act
        when(mockRepository.listAll()).thenReturn(invoices);
        List result = invoiceService.listAllInvoices();

       //Assert
        Assert.assertEquals(invoices.size(),result.size());
    }
    @Test
    public void findAllInvoicesByClientId_whenInvoiceListAndClientIdArePresent_ShouldReturnInvoiceList(){

        //Arrange
        int requestClientId = 1;
        Invoice invoiceOne = new Invoice();
        Client requestedClient = new Client();
        requestedClient.setId(requestClientId);
        Subscriber subscriber = new Subscriber();
        subscriber.setClient(requestedClient);
        invoiceOne.setSubscriber(subscriber);
        Invoice invoiceTwo = new Invoice();
        invoiceTwo.setSubscriber(subscriber);

        List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoiceOne);
        invoices.add(invoiceTwo);

        //Act
        when(mockRepository.findAllInvoicesByClientId(1)).thenReturn(invoices);
        List result = invoiceService.findAllInvoicesByClientId(1);

        //Assert
        Assert.assertEquals(invoices.size(),result.size());

    }

}




