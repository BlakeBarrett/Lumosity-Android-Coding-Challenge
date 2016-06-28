package com.blakebarrett.lumocityandroidcodingchallenge;

import android.test.AndroidTestCase;

import com.blakebarrett.lumocityandroidcodingchallenge.network.PlaceFetcher;
import com.google.android.gms.location.places.Place;

import java.util.List;

/**
 * Created by Blake on 6/26/16.
 */
public class PlaceFetcherTests extends AndroidTestCase {

    public static final String API_RESPONSE_PLACES = "\n" +
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

    public static final String API_RESPONSE_PLACE_INFO = "\n" +
            "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"result\" : {\n" +
            "      \"address_components\" : [\n" +
            "         {\n" +
            "            \"long_name\" : \"5\",\n" +
            "            \"short_name\" : \"5\",\n" +
            "            \"types\" : [ \"floor\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"48\",\n" +
            "            \"short_name\" : \"48\",\n" +
            "            \"types\" : [ \"street_number\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Pirrama Road\",\n" +
            "            \"short_name\" : \"Pirrama Rd\",\n" +
            "            \"types\" : [ \"route\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Pyrmont\",\n" +
            "            \"short_name\" : \"Pyrmont\",\n" +
            "            \"types\" : [ \"locality\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Council of the City of Sydney\",\n" +
            "            \"short_name\" : \"Sydney\",\n" +
            "            \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"New South Wales\",\n" +
            "            \"short_name\" : \"NSW\",\n" +
            "            \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Australia\",\n" +
            "            \"short_name\" : \"AU\",\n" +
            "            \"types\" : [ \"country\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"2009\",\n" +
            "            \"short_name\" : \"2009\",\n" +
            "            \"types\" : [ \"postal_code\" ]\n" +
            "         }\n" +
            "      ],\n" +
            "      \"adr_address\" : \"5, \\u003cspan class=\\\"street-address\\\"\\u003e48 Pirrama Rd\\u003c/span\\u003e, \\u003cspan class=\\\"locality\\\"\\u003ePyrmont\\u003c/span\\u003e \\u003cspan class=\\\"region\\\"\\u003eNSW\\u003c/span\\u003e \\u003cspan class=\\\"postal-code\\\"\\u003e2009\\u003c/span\\u003e, \\u003cspan class=\\\"country-name\\\"\\u003eAustralia\\u003c/span\\u003e\",\n" +
            "      \"formatted_address\" : \"5, 48 Pirrama Rd, Pyrmont NSW 2009, Australia\",\n" +
            "      \"formatted_phone_number\" : \"(02) 9374 4000\",\n" +
            "      \"geometry\" : {\n" +
            "         \"location\" : {\n" +
            "            \"lat\" : -33.8666113,\n" +
            "            \"lng\" : 151.1958324\n" +
            "         }\n" +
            "      },\n" +
            "      \"icon\" : \"https://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
            "      \"id\" : \"4f89212bf76dde31f092cfc14d7506555d85b5c7\",\n" +
            "      \"international_phone_number\" : \"+61 2 9374 4000\",\n" +
            "      \"name\" : \"Google\",\n" +
            "      \"opening_hours\" : {\n" +
            "         \"open_now\" : false,\n" +
            "         \"periods\" : [\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 1,\n" +
            "                  \"time\" : \"1800\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 1,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 2,\n" +
            "                  \"time\" : \"1800\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 2,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 3,\n" +
            "                  \"time\" : \"1800\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 3,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 4,\n" +
            "                  \"time\" : \"1800\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 4,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 5,\n" +
            "                  \"time\" : \"1800\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 5,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            }\n" +
            "         ],\n" +
            "         \"weekday_text\" : [\n" +
            "            \"Monday: 8:00 AM – 6:00 PM\",\n" +
            "            \"Tuesday: 8:00 AM – 6:00 PM\",\n" +
            "            \"Wednesday: 8:00 AM – 6:00 PM\",\n" +
            "            \"Thursday: 8:00 AM – 6:00 PM\",\n" +
            "            \"Friday: 8:00 AM – 6:00 PM\",\n" +
            "            \"Saturday: Closed\",\n" +
            "            \"Sunday: Closed\"\n" +
            "         ]\n" +
            "      },\n" +
            "      \"photos\" : [\n" +
            "         {\n" +
            "            \"height\" : 1365,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/105932078588305868215/photos\\\"\\u003eMaksym Kozlenko\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBcwAAALNfn6GVmyPlDWN2m0-c8q3A6zstFvNNywvang_WVRsgzAyBvIVHUuDrO8weiYkLgR2IZl5VmspbwLHJsBlrRMqzgqYwO7uV_SNBiaa-5U5ngzEQ2MNDxuSgMjk-5cFu3ttMD9y2mnwEQczG0Cvh6A-LItwCemKkHhhsV3UjZ0G1EhA-Uu03MbhOoPBPKwkiYcs-GhQ6FgjkOTRyjzD4AWIc7eRuUkQWDQ\",\n" +
            "            \"width\" : 2048\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 3120,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/116783625253584801301/photos\\\"\\u003eSamson Jabin\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAOI8EjbyDFng7tCwLIa5Ob2s8KnLDb4vpLfBjTdnd20ycvlqkIxkUktAqHDg1qozismkPCL8PY3b8Aa1n5ywECJbnN1ZS0CzuBbZXvHAlkeKkuTXfN2U06InxtEBQowsJDLCvIHsJa3rkfZdJQra7qkGqCLMEncvx85Wmlev37kdEhASQiCYYScO_1EMxaeZrb3AGhRdLMKF1Zh6c9GHDmevshiYRWQgCA\",\n" +
            "            \"width\" : 4160\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 1184,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/106645265231048995466/photos\\\"\\u003eMalik Ahamed\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAGnrGbJafHQA2f1kXZ2gZM4XT684onkZe5YHjAe6GM5HiAde3hjTWqkjAecpXb9gF6_mWqWzqfKtRlBHdmcy7ZJHflQ8-tIBIDQNSUQeAjPF7mZtauW1AJqPzVqUvUaNNIyncWbsayVbsai0RzdNmMCEQs1aZS6PfFmkeSBAp9yMEhCF09oYffKNEtEexEIIs2ZkGhQTAr5mODYmVM_K05CKrwm2dCz4_w\",\n" +
            "            \"width\" : 1776\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 960,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/100919424873665842845/photos\\\"\\u003eDonnie Piercey\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBcwAAAMk44kGznYGQgV88XHv4PTuuQy0IJ5X6_fBrKVmvNXssKGhQ9wYh1I3gB6CZMG_2-jjQbyItjMtz3WTC7_ZRw2Ef6FBcnpsCPi8LL66DQ7QOpnGrkz4_gAcvyj3iawbR-srf_zeojHrqcoavS7nQ5hg-jO6B23MUBWB4ZeX3wRDoEhA89yqhNpLz_3pY3vykfpYTGhRcrZJkHMB2ErjNdsgYeAKSN8580Q\",\n" +
            "            \"width\" : 1280\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 3024,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/117393076859685917096/photos\\\"\\u003eJoann Chu\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAOWno0L-FltWY7lC-vNjPT-hscI8aKfciZYzSljzRCyCS-_PtH4-mkvfs32JkNX7eaeeCx5srClAxvsc374-n8eI78e5gHJcZz02kfvsQ603KroVjSTEZ_A5tvTAJ27nhoEsyOD2RUTTU0kC6ONe5OzHsVCo6ejjzMLLdxWW15wmEhCFMlpeWzYkC68RSHhdYQQ-GhQ0eWsVepHn8Oz6jbbm7cE6NRJlLw\",\n" +
            "            \"width\" : 4032\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 5582,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/110754641211532656340/photos\\\"\\u003eRobert Koch\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAHEBPc_63iMnyIUxhtT7JAk9sPh4bLUnctGu5CuWXwSncOAdxx0RSH3AxhUBrf5m7MAnv_Ps1epWfU827gCXmAzYy7oM8i0eNu_DJM2Ne9yedSBphqrXwRWCg0UsoJHXBHNKv2Ikij9t49Yg0dI0TPzyqC_XdCE0Ebw7Sv3YpMJtEhBb3YSJ6WfMEuDuG_cAs1jyGhRsfIUVoRo2fiFiRIK6_k-wIDXSvA\",\n" +
            "            \"width\" : 2866\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 2988,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/103594026264127040754/photos\\\"\\u003eDaniel Tyson\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAD6xXpKMQtWr7XdvE1BFvScfgA7Z7ycFUDEoH1D03wi6CmyaphSjRFlLrqep15FWzX8l4Rk5oCKyybqvW7mTXF4FQyjdXirQ8j68MGBQiJz0y2pXNswYFLEHYJxIZ5_roIC02XIxtL5g7R8pi3Rf91Irw6K7GXNBtGmkbkWIfuAgEhCSqxEkpHzzMgnBEv2K190XGhQxs47ClYsf5y1XLOga7HTDT4HtAQ\",\n" +
            "            \"width\" : 5312\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 2448,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/116976377324210679577/photos\\\"\\u003eWH CHEN\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAADhg2tYLtnZCJGKgJrOXZVG1A3VmJ4OPLgInv6Qcr3vdgWKOP8MHjAJlp7A8sPx_Q6G9R3naEESrcOW0DN6R6Zehz84tT-HDmzBD4e7dnPM4zMgVfN3AxHDcmBApQAJpRkrDXZbJJTM5bhJ9CDC947qL9e54fw9OnVLA_tyZemxLEhDMqyBuKf66tyXFGfPZ97l9GhQj3fmqYyl5TXL02wVW1-s1d-HW3g\",\n" +
            "            \"width\" : 3264\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 1184,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/106645265231048995466/photos\\\"\\u003eMalik Ahamed\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAHBATVWROSWiNvCT20S-7O76XY-zo_BpdG63M4lrro-dEvvixXtOUC5OlXVbcxhWZuQ5qE4lmvmyWjlUR58VmPgzekg5J022VX6aLJrnmQLRqAQrp72CSUNfyb9-XR8w0oeNOBfsoU4C_R9l4FWSZdgtmKmEkVHnxDBoyfMJ-1TfEhDoar8qOW5qKUmUskEVkGKNGhQEGZMvyX8iID3Zubm5j6cD5lTm8g\",\n" +
            "            \"width\" : 1776\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 4160,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://maps.google.com/maps/contrib/116783625253584801301/photos\\\"\\u003eSamson Jabin\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CoQBdwAAAIKGGHyC6ZWeerPsF0S3pkWVa37TR8kke3nuyMferHwjXJIb1wvY5TeQdbx6f45L5NLvXcpXlkYR_MkeMpy1a7U_nunybqrsfLUfv7CdnCVYfy5XXLd05kuVjeulUQgdNlokfex02zVCK9toImG4yq9MqmU-1Vf_CMLquHLyVfGSEhBqKamRsGrVXjDd3EFphcdcGhTJ5pPbUte5wbUcbhVwunZsnJmZcQ\",\n" +
            "            \"width\" : 3120\n" +
            "         }\n" +
            "      ],\n" +
            "      \"place_id\" : \"ChIJN1t_tDeuEmsRUsoyG83frY4\",\n" +
            "      \"rating\" : 4.6,\n" +
            "      \"reference\" : \"CmRaAAAApMhP6DwVIFJGooMo316b9_47_DQ4ughDmvMnyHj3DLfBLs1_B2A4FoLSOhW8RcF83vVO9Vd5CENJvO7lu2H-mpyIM-2llTHTJboKTpuaa0ymX2yFXi5pK1HbG4rPPVedEhCLQm6gCa31uftV5-6GVD9CGhQyKdYYWWBfsqlF1ri8FsQooSXGmg\",\n" +
            "      \"reviews\" : [\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Nila Sweeney\",\n" +
            "            \"author_url\" : \"https://plus.google.com/115167791509064907883\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"//lh4.googleusercontent.com/-P-5LDZmJbig/AAAAAAAAAAI/AAAAAAAAlSc/TmYuHFZvLlI/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"text\" : \"Amazing place!  So incredibly vibrant and hip. I love the way the office have been laid out. The way work-life balance is encouraged is truly impressive!  Google, please hire me! \",\n" +
            "            \"time\" : 1464213084\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Keith Hong\",\n" +
            "            \"author_url\" : \"https://plus.google.com/106314339785578978358\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"//lh3.googleusercontent.com/-Z69cMMxhr-c/AAAAAAAAAAI/AAAAAAAAAEs/CbphM5oqKas/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"text\" : \"Not often you get invited to the google Australia HQ. Neat spot. Love their Pirrama place but their ODI location is pretty cool too (and soon overtakin most of Fairfax) \",\n" +
            "            \"time\" : 1464676368\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Amit Singh\",\n" +
            "            \"author_url\" : \"https://plus.google.com/102535162210581770614\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"//lh6.googleusercontent.com/--YY2UrDGYPI/AAAAAAAAAAI/AAAAAAAAMwM/YxzxcrCmVrY/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"text\" : \"I just loved it to be here. Awesome creativity and concepts.\\nAnd there's a book shelf where there's a book. If you press, a hidden room opens. And there's a surprise in this room.\",\n" +
            "            \"time\" : 1464169914\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Daniel Tyson\",\n" +
            "            \"author_url\" : \"https://plus.google.com/103594026264127040754\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"//lh5.googleusercontent.com/-ZkbIzFDl9qA/AAAAAAAAAAI/AAAAAAAAeAc/3x8bjXRAJy4/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"text\" : \"Always great to visit. It's closed off for the most part, unless you know a Googler or have a reason to visit. There's a Google logo on the wall in the Foyer and a Google Maps Trike opposite the lift. The coffee shop downstairs also has Google coloured stools as well.\",\n" +
            "            \"time\" : 1460410703\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Justine OBRIEN\",\n" +
            "            \"author_url\" : \"https://plus.google.com/104177669626132953795\",\n" +
            "            \"language\" : \"en\",\n" +
            "            \"profile_photo_url\" : \"//lh6.googleusercontent.com/-s6AzNe5Qcco/AAAAAAAAAAI/AAAAAAAAFTE/NvVzCuI-jMI/photo.jpg\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"text\" : \"Google Sydney is located on Darling Island on the glorious Sydney Harbour in a prime position easy to get to for staff and visitors. The reception has an excellent fresh living wall and the staff are welcoming, pleasant and friendly, ready to assist you with bountiful information for all inquiries. All ready to *do the right thing* Always helping *Go Get IT*, the right information to *Gather IT*, from all the right places plus *Give IT* at all the right times to all the right people! \\nGo Get Gather Give Google excellence personified at Google Sydney! Thanks Google Sydney!\",\n" +
            "            \"time\" : 1451482843\n" +
            "         }\n" +
            "      ],\n" +
            "      \"scope\" : \"GOOGLE\",\n" +
            "      \"types\" : [ \"point_of_interest\", \"establishment\" ],\n" +
            "      \"url\" : \"https://maps.google.com/?cid=10281119596374313554\",\n" +
            "      \"utc_offset\" : 600,\n" +
            "      \"vicinity\" : \"5 48 Pirrama Road, Pyrmont\",\n" +
            "      \"website\" : \"https://www.google.com.au/about/careers/locations/sydney/\"\n" +
            "   },\n" +
            "   \"status\" : \"OK\"\n" +
            "}\n";

    public void setup() {

    }

    public void tearDown() {

    }

    public void testParsePlaces() {
        final List<Place> resultants = PlaceFetcher.placesFromJson(API_RESPONSE_PLACES);
        if (resultants == null) {
            return;
        }
        for (int i = 0; i < resultants.size(); i++) {
            Place currentPlace = resultants.get(i);
            assertNotNull(currentPlace);
        }
        assertNotNull(resultants);
    }

    public void testParsePlaceInfo() {
        final Place place = PlaceFetcher.placeFromPlaceInfoJson(API_RESPONSE_PLACE_INFO);
        assertNotNull(place);
    }

}
