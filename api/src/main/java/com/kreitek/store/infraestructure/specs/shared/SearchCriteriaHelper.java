package com.kreitek.store.infraestructure.specs.shared;

import java.util.ArrayList;
import java.util.List;

public  class SearchCriteriaHelper {

    public static final int EXPECTED_SEGMENTS_NUMBER = 3;
    public static final int KEY_SEGMENT_POSITION = 0;
    public static final int OPERATION_SEGMENT_POSITION = 1;
    public static final int VALUE_SEGMENT_POSITION = 2;
    public static final String SEGMENTS_DIVISOR = ",";
    public static final String ATTRIBUTES_DIVISOR = ":";

    public static List<SearchCriteria> fromFilterString(String filter) {
        List<SearchCriteria> criterias = new ArrayList<>();
        int i;

        if (filter == null) { return criterias; }

        String[] filterSegments = filter.split(SEGMENTS_DIVISOR);

        for (i = 0; i < filterSegments.length; i++) {
            String[] criteriaSegments = filterSegments[i].split(ATTRIBUTES_DIVISOR);

            if (criteriaSegments.length == EXPECTED_SEGMENTS_NUMBER) {
                SearchCriteria criteria = new SearchCriteria();
                criteria.setKey(criteriaSegments[KEY_SEGMENT_POSITION]);
                criteria.setOperation(SearchOperation.valueOf(criteriaSegments[OPERATION_SEGMENT_POSITION]));
                criteria.setValue(criteriaSegments[VALUE_SEGMENT_POSITION]);

                criterias.add(criteria);
            }
        }

        return criterias;
    }
}
