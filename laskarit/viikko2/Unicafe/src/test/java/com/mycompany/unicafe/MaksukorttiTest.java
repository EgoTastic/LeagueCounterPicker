package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoOikein(){
        assertEquals(10, kortti.saldo());
    }
    
    @Test
    public void kortinSaldoAlussa() {
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void kortinSaldoKasvaa() {
        kortti.lataaRahaa(5);
        assertEquals("saldo: 0.15", kortti.toString());
    }
    
    @Test
    public void saldoLaskeeOttaessaOikein() {
        kortti.otaRahaa(5);
        assertEquals("saldo: 0.05", kortti.toString());
    }
    
    @Test
    public void saldoEiLaskeKunOttaaVaarin(){
        kortti.otaRahaa(12);
        assertEquals("saldo: 0.10", kortti.toString());
    }
    
    @Test
    public void korttiPalauttaaTrueOikein(){
        assertEquals(true, kortti.otaRahaa(5));
    }
    
    @Test
    public void korttiPalauttaaFalseOikein(){
        assertEquals(false, kortti.otaRahaa(15));
    }
    
}
