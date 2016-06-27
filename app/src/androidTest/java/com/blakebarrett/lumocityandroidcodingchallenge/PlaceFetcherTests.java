package com.blakebarrett.lumocityandroidcodingchallenge;

import android.test.AndroidTestCase;

import com.blakebarrett.lumocityandroidcodingchallenge.network.PlaceFetcher;
import com.google.android.gms.location.places.Place;

import java.util.List;

/**
 * Created by Blake on 6/26/16.
 */
public class PlaceFetcherTests extends AndroidTestCase {

    public static final String API_RESPONSE = "\n" +
            "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"results\" : [\n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : -33.86755700000001,\n" +
            "               \"lng\" : 151.201527\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
            "         \"id\" : \"ce4ffe228ab7ad49bb050defe68b3d28cc879c4a\",\n" +
            "         \"name\" : \"Sydney Showboats\",\n" +
            "         \"opening_hours\" : {\n" +
            "            \"open_now\" : true,\n" +
            "            \"weekday_text\" : []\n" +
            "         },\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 250,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/107415973755376511005/photos\\\"\\u003eSydney Showboats\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CoQBcwAAAD0iWeWs77IXgftcFarMmcBSgYUgcIyQWX-Upp8JsFoiYCywi7CQ2yOOE9KGxbHjlVdrocPhLajc3f_95gF8_WUMgSHYR74PP0j28tcMYAqJpI621wEzJweIkX9dE79fa6pNZewmgUle6u_o7zldIyQP0ef3J78ey4vLGrz6BMX3EhD0amNMWqJq0VyaMHtGa9c6GhTYqGMPwwzlkZ9dNzbtj3Dn8MxmaQ\",\n" +
            "               \"width\" : 250\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJjRuIiTiuEmsRCHhYnrWiSok\",\n" +
            "         \"rating\" : 4.3,\n" +
            "         \"reference\" : \"CnRkAAAASnPLxbZaG_hw8lBEe2Sckz0aEsVk8AxvHNCQuDOeSXn9s2wuEE4E0nDFM68LzV4yO4yhJXlotOjPRGTMAT2AGjQmOevYpBSHa9Eswtd5-yja0D07Sx8HnOy3oZeEOkzg3zstLce6f81l6UOd6gRq3hIQk0LVs6a8bx39rFwckINmChoUUjWHIrPKrlemjy0Idx6YN9qdIzE\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [\n" +
            "            \"travel_agency\",\n" +
            "            \"restaurant\",\n" +
            "            \"food\",\n" +
            "            \"point_of_interest\",\n" +
            "            \"establishment\"\n" +
            "         ],\n" +
            "         \"vicinity\" : \"King Street Wharf 5, Lime Street, Sydney\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : -33.8709434,\n" +
            "               \"lng\" : 151.1903114\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
            "         \"id\" : \"3458f23c154e574552e0722773a46f384816b241\",\n" +
            "         \"name\" : \"Vagabond Cruises\",\n" +
            "         \"opening_hours\" : {\n" +
            "            \"open_now\" : false,\n" +
            "            \"weekday_text\" : []\n" +
            "         },\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 1067,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/101516907347198229066/photos\\\"\\u003eVagabond Cruises\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CoQBcwAAAMhbLLPcKDpYq0B-kzz9CsrC-ECk6JNvbqGFBsUTUDlTgNVy6nikDcJO1BrR1SGyWB8zA9M3kx454nGSiiY8ZcTgyR5rXNcXebmQgAN8kPhjnmHqqu2mZavocvYBV0-NY8WEA4JAEODtZx7UgVT_Ts9gD3NXry2uGjsOLJzZRhV2EhCjqf-JpmAv1kiN88uUPaajGhTNG3in_U1ra6M3YJqnIL5gwd1GQg\",\n" +
            "               \"width\" : 1600\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJLfySpTOuEmsRMFymbMkVkOE\",\n" +
            "         \"rating\" : 4.1,\n" +
            "         \"reference\" : \"CnRkAAAA1G6rz2OD2_KMZemOhDzm8RTrA913NuRZZjv2TQL5DeX_KXGuXRAKh0uMjoqs4l0GsN3CEw-LIlcEDuuuiMszjKtnBZwbhGNLpqG8GaBwhjEy8--4uaOuzaObwqYnobXNIAHWyqtl2tjTTu9eHlGH_xIQ_B8aOIzxx0av4Q6xxzExFxoUq9SkliKZ1tfbgbbuhB3YFTAk-wo\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [\n" +
            "            \"travel_agency\",\n" +
            "            \"restaurant\",\n" +
            "            \"food\",\n" +
            "            \"point_of_interest\",\n" +
            "            \"establishment\"\n" +
            "         ],\n" +
            "         \"vicinity\" : \"37 Bank Street, Pyrmont\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : -33.8686058,\n" +
            "               \"lng\" : 151.2018207\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
            "         \"id\" : \"21a0b251c9b8392186142c798263e289fe45b4aa\",\n" +
            "         \"name\" : \"Rhythmboat Cruises\",\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 480,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/104066891898402903288/photos\\\"\\u003eRhythmboat Cruises\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CoQBcwAAAAYwvDNBAJuxOzvmIA3iwnvknKeRaavXCDDrkAoNVA19wlmQbn2VpRZp2yw6TYiAUyakJ9qR-_H3g2Dx3jeW3I-ZIa0cPjnJp4T-niYqCXlssHh_sgSONeDtAUqIfzrNgZGHP-OcqvNR68h0WtsOqu0Qi7LncPb-PWZwvCr_IXr-EhBuCOugyAcgbb_ildMhNMq9GhRiTNB9SqgS65aRINaahEyhEuENeA\",\n" +
            "               \"width\" : 640\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJyWEHuEmuEmsRm9hTkapTCrk\",\n" +
            "         \"rating\" : 4.2,\n" +
            "         \"reference\" : \"CnRmAAAAcjNHDHftO9jI4TyXZawMEFEcjsZb91Qb9tZgkX8mkybXTZ5eUTeqdniM6eQuu-z5ODzKJTv_rEWYAW4uqnh1bOc8I2DWtuR4yKRfv2wVXVB-Y3MUWB4-tcuHLnFc8sEoqcbACHimPiM3sPIJ1wA8SxIQAqE8QbLB99qJqMnlPz0B3RoUEMy8pzT11tDj3MOX2Fkytz3y-Ls\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [\n" +
            "            \"travel_agency\",\n" +
            "            \"restaurant\",\n" +
            "            \"food\",\n" +
            "            \"point_of_interest\",\n" +
            "            \"establishment\"\n" +
            "         ],\n" +
            "         \"vicinity\" : \"King Street Wharf, King Street, Sydney\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : -33.867591,\n" +
            "               \"lng\" : 151.201196\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
            "         \"id\" : \"a97f9fb468bcd26b68a23072a55af82d4b325e0d\",\n" +
            "         \"name\" : \"Australian Cruise Group\",\n" +
            "         \"opening_hours\" : {\n" +
            "            \"open_now\" : true,\n" +
            "            \"weekday_text\" : []\n" +
            "         },\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 328,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/110751364053842618118/photos\\\"\\u003eAustralian Cruise Group\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CoQBcwAAAGuLTuqHN8nDRto--_kL9bBw8_WME4l0coTAhAmh9hjUSq9c7O8qtgNkgJACWpoYdkDVdm5omAuQ6wDXBDYmKmCLqVb3xVG5QXv6ykxvznMdVS0_mXiI9CwMrH3n7IL5HD6KxDIgKeFb1RFl12Z06XZBS3KoQIPzHAFIOasfwChJEhAP2QPqMXo7t4CCReiG906oGhQAjxiqbZ0cW0AxElYgujVBd55Xtg\",\n" +
            "               \"width\" : 329\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJrTLr-GyuEmsRBfy61i59si0\",\n" +
            "         \"reference\" : \"CnRqAAAAcW-qQPdlcmdG0lO8vYJC2v2zPF_-BdRfImYw2-Xpa_c4ajVHuiRIC1vpbsX6u0SIDAFxmhloaKaiNsrYtR_rlvMz-wb7nqvlrsR4dAn4kTndMcgc-hMRELV76RebztKomGu5RFBQN1M7Q4NY_WGTbhIQZS0y83k9NOAGSo9lEMHCsxoUY9OISv5V00FG9pWMnuN4FKjOM2Q\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [\n" +
            "            \"travel_agency\",\n" +
            "            \"restaurant\",\n" +
            "            \"food\",\n" +
            "            \"point_of_interest\",\n" +
            "            \"establishment\"\n" +
            "         ],\n" +
            "         \"vicinity\" : \"32 The Promenade, King Street Wharf 5, Sydney\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : -33.867551,\n" +
            "               \"lng\" : 151.200817\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
            "         \"id\" : \"b0277cade7696e575824681aba949d68814f9efe\",\n" +
            "         \"name\" : \"Sydney New Year's Eve Cruises\",\n" +
            "         \"opening_hours\" : {\n" +
            "            \"open_now\" : false,\n" +
            "            \"weekday_text\" : []\n" +
            "         },\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 1152,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/107666140764925298472/photos\\\"\\u003eSydney New Year&#39;s Eve Cruises\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CoQBcwAAAFcD8ym_7VDgVZcwz-LPn7gyp5R9pQrBVwat8kzI03WY4MQbb8KgaWxzM81KtOIOTRS_cvJPtXNZZixdePb7fMKKo0A50yIKsCGMFvMZ9TM-56qQosyDBbDCBo3of53H-jr0EkC2w9iEDOthmHXBiV39uoH7y-vwwWrlRw7Se4oZEhDOyLU5HgduoeYe57p8GxoGGhTLJADvzjPj2fe3CXjCL3-kmJ3UlA\",\n" +
            "               \"width\" : 2048\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJ__8_hziuEmsR27ucFXECfOg\",\n" +
            "         \"reference\" : \"CoQBcQAAAAhC1KVMml3Jwl2LX4LVAecwNgRMcFIuDSIJswbtls2J0mZ1Uw_1U3-EZ37PZAneX4QM9GRcVs47HpiLZ4lg8DVFwiYYdp0suiLgcla-LuV649CBKVnT_G1viZ_fme0Q9rd50MzQUPFi2lAuWi--450yrYHkSgq47x6GnnRRv3QXEhC4QB7GBADKKnQPd-DDMil8GhRmQehFI4dKEEr5e3CRRs1mApQmEQ\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [\n" +
            "            \"travel_agency\",\n" +
            "            \"restaurant\",\n" +
            "            \"food\",\n" +
            "            \"point_of_interest\",\n" +
            "            \"establishment\"\n" +
            "         ],\n" +
            "         \"vicinity\" : \"32 The Promenade, King Street Wharf 5, Sydney Nsw 2000, Sydney\"\n" +
            "      },\n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : -33.86724419999999,\n" +
            "               \"lng\" : 151.2017012\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png\",\n" +
            "         \"id\" : \"c620902a8565dd4c4d605fecbe4f9b585d881b62\",\n" +
            "         \"name\" : \"Blue Line Cruises\",\n" +
            "         \"place_id\" : \"ChIJvwSIiTiuEmsR8hEazPa4W7U\",\n" +
            "         \"reference\" : \"CnRlAAAATQ2wPX72JfEJrRncjbHogQXbUzkTHUd9mYuZxcBPyzzIfaHi5Gf6V4N5SfouJJ78TlfQnKs6E4AmMwF7bYn4nvSt8uwc0TZJQr5BccEs4Ad9gKNygXAVX2NZsd0Tvq7ACGZ6P1cujfEO0U-yIdNDzBIQadhgguOO7eat4ML1UgHFaRoU3XDtBWtFnRJcQmjDd-NjaVbqF3A\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [ \"restaurant\", \"food\", \"point_of_interest\", \"establishment\" ],\n" +
            "         \"vicinity\" : \"Australia\"\n" +
            "      }\n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}\n";

    public void setup() {

    }

    public void tearDown() {

    }

    public void testParse() {
        final List<Place> resultants = PlaceFetcher.fromJson(API_RESPONSE);
        for (int i = 0; i < resultants.size(); i++) {
            Place currentPlace = resultants.get(i);
            assertNotNull(currentPlace);
        }
        assertNotNull(resultants);
    }

}
