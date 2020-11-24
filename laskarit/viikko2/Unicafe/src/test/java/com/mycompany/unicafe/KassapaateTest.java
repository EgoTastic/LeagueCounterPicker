/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.unicafe;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author miikakoskela
 */
public class KassapaateTest {
    
    Kassapaate kassa;
    
    
    
    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }
    
    @Test
    public void onkoKassaOlemassa(){
        assertTrue(kassa!=null);
    }
    
    @Test
    public void syoEdullisestiRiittavaKateinen(){
        assertEquals(10, kassa.syoEdullisesti(250));
    }
    
    @Test
    public void syoEdullisestiEiRiitaKateinen(){
        assertEquals(100, kassa.syoEdullisesti(100));
    }
    
    @Test
    public void syoMaukkaastiRiittavaKateinen(){
        assertEquals(10, kassa.syoMaukkaasti(410));
    }
    
    @Test
    public void syoMaukkaastiEiRiitaKateinne(){
        assertEquals(100, kassa.syoMaukkaasti(100));
    }
    
    @Test
    public void syoEdullisestiRiittavaKortti(){
        Maksukortti kortti = new Maksukortti(500);
        assertTrue(kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoEdullisestiEiRiitaKortti(){
        Maksukortti kortti = new Maksukortti(100);
        assertTrue(!kassa.syoEdullisesti(kortti));
    }
    
    @Test
    public void syoMaukkaastiRiittavaKortti(){
        Maksukortti kortti = new Maksukortti(500);
        assertTrue(kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void syoMaukkaastiEiRiitaKortti(){
        Maksukortti kortti = new Maksukortti(100);
        assertTrue(!kassa.syoMaukkaasti(kortti));
    }
    
    @Test
    public void korttiEdullinenEiNostaKassaa(){
        Maksukortti kortti = new Maksukortti(240);
        kassa.syoEdullisesti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void korttiMaukasEiNostaKassaa(){
        Maksukortti kortti = new Maksukortti(400);
        kassa.syoMaukkaasti(kortti);
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void lataaRahaaOikein(){
        Maksukortti kortti = new Maksukortti(100);
        kassa.lataaRahaaKortille(kortti, 10);
        assertEquals(110, kortti.saldo());
    }
    
    @Test
    public void lataaRahaaVaarin(){
        Maksukortti kortti = new Maksukortti(100);
        kassa.lataaRahaaKortille(kortti, -10);
        assertEquals(100, kortti.saldo());
    }
    
    @Test
    public void edullisiaMaaraOikein(){
        Maksukortti kortti = new Maksukortti(240);
        kassa.syoEdullisesti(240);
        kassa.syoEdullisesti(kortti);
        assertEquals(2, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaastiMaaraOikein(){
        Maksukortti kortti = new Maksukortti(400);
        kassa.syoMaukkaasti(400);
        kassa.syoMaukkaasti(kortti);
        assertEquals(2, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassaRahaaAlussaOikein(){
        assertEquals(100000, kassa.kassassaRahaa());
    }
    
    @Test
    public void edullisiaAlussa(){
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void maukkaitaAlussa(){
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void kassassaRahaaOikeinEdullinen(){
        kassa.syoEdullisesti(240);
        assertEquals(100240, kassa.kassassaRahaa());
    }
    
    @Test
    public void kassassaRahaaOikeinMaukas() {
        kassa.syoMaukkaasti(400);
        assertEquals(100400, kassa.kassassaRahaa());
    }

}
