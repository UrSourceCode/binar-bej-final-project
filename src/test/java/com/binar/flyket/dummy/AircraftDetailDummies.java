package com.binar.flyket.dummy;

import com.binar.flyket.dto.model.AircraftDetailDTO;
import com.binar.flyket.model.AircraftClass;
import com.binar.flyket.model.AircraftDetail;

import java.math.BigDecimal;
import java.util.List;

public class AircraftDetailDummies {

    public static List<AircraftDetail> getAll() {
        AircraftDetail acd1 = new AircraftDetail();
        acd1.setId("acd1");
        acd1.setPrice(BigDecimal.valueOf(1_000_000));
        acd1.setAircraftClass(AircraftClass.FIRST);
        acd1.setAircraft(AircraftDummies.aircraftList().get(0));
        acd1.setMaxCabin(10);
        acd1.setMaxBaggage(7);

        AircraftDetail acd2 = new AircraftDetail();
        acd2.setId("acd2");
        acd2.setPrice(BigDecimal.valueOf(800_000));
        acd2.setAircraftClass(AircraftClass.ECONOMY);
        acd2.setAircraft(AircraftDummies.aircraftList().get(1));
        acd2.setMaxCabin(10);
        acd2.setMaxBaggage(7);

        AircraftDetail acd3 = new AircraftDetail();
        acd3.setId("acd3");
        acd3.setPrice(BigDecimal.valueOf(2_000_000));
        acd3.setAircraft(AircraftDummies.aircraftList().get(0));
        acd3.setAircraftClass(AircraftClass.BUSINESS);
        acd3.setMaxCabin(10);
        acd3.setMaxBaggage(7);

        return List.of(acd1, acd2, acd3);
    }

    public static List<AircraftDetailDTO> aircraftDetailDTOList() {
        AircraftDetail aircraft = getAll().get(0);

        AircraftDetailDTO acdDTO1 = new AircraftDetailDTO();
        acdDTO1.setAircraftClass(aircraft.getAircraftClass());
        acdDTO1.setAircraftType(aircraft.getAircraft().getType());
        acdDTO1.setPrice(aircraft.getPrice());
        acdDTO1.setMaxCabin(aircraft.getMaxCabin());
        acdDTO1.setMaxBaggage(aircraft.getMaxBaggage());

        aircraft = getAll().get(1);

        AircraftDetailDTO acdDTO2 = new AircraftDetailDTO();
        acdDTO2.setAircraftClass(aircraft.getAircraftClass());
        acdDTO2.setAircraftType(aircraft.getAircraft().getType());
        acdDTO2.setPrice(aircraft.getPrice());
        acdDTO2.setMaxCabin(aircraft.getMaxCabin());
        acdDTO2.setMaxBaggage(aircraft.getMaxBaggage());

        aircraft = getAll().get(2);

        AircraftDetailDTO acdDTO3 = new AircraftDetailDTO();
        acdDTO3.setAircraftClass(aircraft.getAircraftClass());
        acdDTO3.setAircraftType(aircraft.getAircraft().getType());
        acdDTO3.setPrice(aircraft.getPrice());
        acdDTO3.setMaxCabin(aircraft.getMaxCabin());
        acdDTO3.setMaxBaggage(aircraft.getMaxBaggage());

        return List.of(acdDTO1, acdDTO2, acdDTO3);
    }
}
