import { IBioGuidline } from './interfaces/Ireport';

export default class SharedConstats {
    // public static url = 'http://localhost:8080/mhc_template/rest';
    public static url = 'http://localhost:3000';
    public static fakeUrl = 'http://localhost:3000';
    public static localUrl = 'http://localhost:8080/comed/rest/private';

    public static guidlines: IBioGuidline =
        {
            sistolic: [
                {
                    color: 'green',
                    min: 0,
                    max: 120,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 121,
                    max: 139,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 140,
                    max: 159,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 159,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ],
            diastolic: [
                {
                    color: 'green',
                    min: 0,
                    max: 80,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 81,
                    max: 89,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 90,
                    max: 99,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 99,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ],
            cholesterol: [
                {
                    color: 'green',
                    min: 0,
                    max: 80,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 81,
                    max: 89,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 90,
                    max: 99,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 99,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ],
            hdl: [
                {
                    color: 'green',
                    min: 0,
                    max: 80,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 81,
                    max: 89,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 90,
                    max: 99,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 99,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ],
            ldl: [
                {
                    color: 'green',
                    min: 0,
                    max: 80,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 81,
                    max: 89,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 90,
                    max: 99,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 99,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ], triglycerides: [
                {
                    color: 'green',
                    min: 0,
                    max: 80,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 81,
                    max: 89,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 90,
                    max: 99,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 99,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ],
            fasting: [
                {
                    color: 'green',
                    min: 0,
                    max: 80,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 81,
                    max: 89,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 90,
                    max: 99,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 99,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ],
            hba1c: [
                {
                    color: 'green',
                    min: 0,
                    max: 80,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 81,
                    max: 89,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 90,
                    max: 99,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 99,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ],
            bmi: [
                {
                    color: 'green',
                    min: 0,
                    max: 80,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 81,
                    max: 89,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 90,
                    max: 99,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 99,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ],
            waist: [
                {
                    color: 'green',
                    min: 0,
                    max: 80,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 81,
                    max: 89,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 90,
                    max: 99,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 99,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ],
            body_fat: [
                {
                    color: 'green',
                    min: 0,
                    max: 80,
                    name: 'Normal'
                },
                {
                    color: 'yellow',
                    min: 81,
                    max: 89,
                    name: 'Prehypertension'
                },
                {
                    color: 'orange',
                    min: 90,
                    max: 99,
                    name: 'Stage 1 Hypertension'
                }
                ,
                {
                    color: 'red',
                    min: 99,
                    max: 100000,
                    name: 'Stage 2 Hypertension'
                }
            ]
        };
}




