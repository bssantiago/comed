import { IBioGuidline } from './interfaces/Ireport';

export default class SharedConstats {
    // public static url = 'http://localhost:8080/mhc_template/rest';
    public static url = 'http://localhost:3000';
    public static fakeUrl = 'http://localhost:3000';
    public static localUrl = 'http://localhost:8080/comed/rest/private';

    public static nvd3Style = `.nvd3 .nv-axis {
        pointer-events:none;
        opacity: 1;
    }

    .nvd3 .nv-axis path {
        fill: none;
        stroke: #000;
        stroke-opacity: .75;
        shape-rendering: crispEdges;
    }

    .nvd3 .nv-axis path.domain {
        stroke-opacity: .75;
    }

    .nvd3 .nv-axis.nv-x path.domain {
        stroke-opacity: 0;
    }

    .nvd3 .nv-axis line {
        fill: none;
        stroke: #e5e5e5;
        shape-rendering: crispEdges;
    }

    .nvd3 .nv-axis .zero line,
        /*this selector may not be necessary*/ .nvd3 .nv-axis line.zero {
        stroke-opacity: .75;
    }

    .nvd3 .nv-axis .nv-axisMaxMin text {
        font-weight: bold;
    }

    .nvd3 .x  .nv-axis .nv-axisMaxMin text,
    .nvd3 .x2 .nv-axis .nv-axisMaxMin text,
    .nvd3 .x3 .nv-axis .nv-axisMaxMin text {
        text-anchor: middle;
    }

    .nvd3 .nv-axis.nv-disabled {
        opacity: 0;
    }

    .nvd3 .nv-bars rect {
        fill-opacity: .75;

        transition: fill-opacity 250ms linear;
    }

    .nvd3 .nv-bars rect.hover {
        fill-opacity: 1;
    }

    .nvd3 .nv-bars .hover rect {
        fill: lightblue;
    }

    .nvd3 .nv-bars text {
        fill: rgba(0,0,0,0);
    }

    .nvd3 .nv-bars .hover text {
        fill: rgba(0,0,0,1);
    }

    .nvd3 .nv-multibar .nv-groups rect,
    .nvd3 .nv-multibarHorizontal .nv-groups rect,
    .nvd3 .nv-discretebar .nv-groups rect {
        stroke-opacity: 0;

        transition: fill-opacity 250ms linear;
    }

    .nvd3 .nv-multibar .nv-groups rect:hover,
    .nvd3 .nv-multibarHorizontal .nv-groups rect:hover,
    .nvd3 .nv-candlestickBar .nv-ticks rect:hover,
    .nvd3 .nv-discretebar .nv-groups rect:hover {
        fill-opacity: 1;
    }

    .nvd3 .nv-discretebar .nv-groups text,
    .nvd3 .nv-multibarHorizontal .nv-groups text {
        font-weight: bold;
        fill: rgba(0,0,0,1);
        stroke: rgba(0,0,0,0);
    }

    /* boxplot CSS */
    .nvd3 .nv-boxplot circle {
      fill-opacity: 0.5;
    }

    .nvd3 .nv-boxplot circle:hover {
      fill-opacity: 1;
    }

    .nvd3 .nv-boxplot rect:hover {
      fill-opacity: 1;
    }

    .nvd3 line.nv-boxplot-median {
      stroke: black;
    }

    .nv-boxplot-tick:hover {
      stroke-width: 2.5px;
    }
    /* bullet */
    .nvd3.nv-bullet { font: 10px sans-serif; }
    .nvd3.nv-bullet .nv-measure { fill-opacity: .8; }
    .nvd3.nv-bullet .nv-measure:hover { fill-opacity: 1; }
    .nvd3.nv-bullet .nv-marker { stroke: #000; stroke-width: 2px; }
    .nvd3.nv-bullet .nv-markerTriangle { stroke: #000; fill: #fff; stroke-width: 1.5px; }
    .nvd3.nv-bullet .nv-markerLine { stroke: #000; stroke-width: 1.5px; }
    .nvd3.nv-bullet .nv-tick line { stroke: #666; stroke-width: .5px; }
    .nvd3.nv-bullet .nv-range.nv-s0 { fill: #eee; }
    .nvd3.nv-bullet .nv-range.nv-s1 { fill: #ddd; }
    .nvd3.nv-bullet .nv-range.nv-s2 { fill: #ccc; }
    .nvd3.nv-bullet .nv-title { font-size: 14px; font-weight: bold; }
    .nvd3.nv-bullet .nv-subtitle { fill: #999; }

    .nvd3.nv-bullet .nv-range {
        fill: #bababa;
        fill-opacity: .4;
    }

    .nvd3.nv-bullet .nv-range:hover {
        fill-opacity: .7;
    }

    .nvd3.nv-candlestickBar .nv-ticks .nv-tick {
        stroke-width: 1px;
    }

    .nvd3.nv-candlestickBar .nv-ticks .nv-tick.hover {
        stroke-width: 2px;
    }

    .nvd3.nv-candlestickBar .nv-ticks .nv-tick.positive rect {
        stroke: #2ca02c;
        fill: #2ca02c;
    }

    .nvd3.nv-candlestickBar .nv-ticks .nv-tick.negative rect {
        stroke: #d62728;
        fill: #d62728;
    }

    .with-transitions .nv-candlestickBar .nv-ticks .nv-tick {
        transition: stroke-width 250ms linear, stroke-opacity 250ms linear;
    }

    .nvd3.nv-candlestickBar .nv-ticks line {
        stroke: #333;
    }

    .nv-force-node {
        stroke: #fff;
        stroke-width: 1.5px;
    }

    .nv-force-link {
        stroke: #999;
        stroke-opacity: .6;
    }

    .nv-force-node text {
        stroke-width: 0px;
    }

    .nvd3 .nv-legend .nv-disabled rect {
        /*fill-opacity: 0;*/
    }

    .nvd3 .nv-check-box .nv-box {
        fill-opacity:0;
        stroke-width:2;
    }

    .nvd3 .nv-check-box .nv-check {
        fill-opacity:0;
        stroke-width:4;
    }

    .nvd3 .nv-series.nv-disabled .nv-check-box .nv-check {
        fill-opacity:0;
        stroke-opacity:0;
    }

    .nvd3 .nv-controlsWrap .nv-legend .nv-check-box .nv-check {
        opacity: 0;
    }

    /* line plus bar */
    .nvd3.nv-linePlusBar .nv-bar rect {
        fill-opacity: .75;
    }

    .nvd3.nv-linePlusBar .nv-bar rect:hover {
        fill-opacity: 1;
    }
    .nvd3 .nv-groups path.nv-line {
        fill: none;
    }

    .nvd3 .nv-groups path.nv-area {
        stroke: none;
    }

    .nvd3.nv-line .nvd3.nv-scatter .nv-groups .nv-point {
        fill-opacity: 0;
        stroke-opacity: 0;
    }

    .nvd3.nv-scatter.nv-single-point .nv-groups .nv-point {
        fill-opacity: .5 !important;
        stroke-opacity: .5 !important;
    }


    .with-transitions .nvd3 .nv-groups .nv-point {
        transition: stroke-width 250ms linear, stroke-opacity 250ms linear;
    }

    .nvd3.nv-scatter .nv-groups .nv-point.hover,
    .nvd3 .nv-groups .nv-point.hover {
        stroke-width: 7px;
        fill-opacity: .95 !important;
        stroke-opacity: .95 !important;
    }


    .nvd3 .nv-point-paths path {
        stroke: #aaa;
        stroke-opacity: 0;
        fill: #eee;
        fill-opacity: 0;
    }


    .nvd3 .nv-indexLine {
        cursor: ew-resize;
    }

    /********************
     * SVG CSS
     */

    /********************
      Default CSS for an svg element nvd3 used
    */
    svg.nvd3-svg {
        -webkit-user-select: none;
           -moz-user-select: none;
            -ms-user-select: none;
                user-select: none;
        display: block;
        width:100%;
        height:100%;
    }

    /********************
      Box shadow and border radius styling
    */
    .nvtooltip.with-3d-shadow, .with-3d-shadow .nvtooltip {
        box-shadow: 0 5px 10px rgba(0,0,0,.2);
        border-radius: 5px;
    }


    .nvd3 text {
        font: normal 12px Arial, sans-serif;
    }

    .nvd3 .title {
        font: bold 14px Arial, sans-serif;
    }

    .nvd3 .nv-background {
        fill: white;
        fill-opacity: 0;
    }

    .nvd3.nv-noData {
        font-size: 18px;
        font-weight: bold;
    }


    /**********
    *  Brush
    */

    .nv-brush .extent {
        fill-opacity: .125;
        shape-rendering: crispEdges;
    }

    .nv-brush .resize path {
        fill: #eee;
        stroke: #666;
    }


    /**********
    *  Legend
    */

    .nvd3 .nv-legend .nv-series {
        cursor: pointer;
    }

    .nvd3 .nv-legend .nv-disabled circle {
        fill-opacity: 0;
    }

    /* focus */
    .nvd3 .nv-brush .extent {
        fill-opacity: 0 !important;
    }

    .nvd3 .nv-brushBackground rect {
        stroke: #000;
        stroke-width: .4;
        fill: #fff;
        fill-opacity: .7;
    }

    /**********
    *  Print
    */

    @media print {
        .nvd3 text {
            stroke-width: 0;
            fill-opacity: 1;
        }
    }

    .nvd3.nv-ohlcBar .nv-ticks .nv-tick {
        stroke-width: 1px;
    }

    .nvd3.nv-ohlcBar .nv-ticks .nv-tick.hover {
        stroke-width: 2px;
    }

    .nvd3.nv-ohlcBar .nv-ticks .nv-tick.positive {
        stroke: #2ca02c;
    }

    .nvd3.nv-ohlcBar .nv-ticks .nv-tick.negative {
        stroke: #d62728;
    }


    .nvd3 .background path {
        fill: none;
        stroke: #EEE;
        stroke-opacity: .4;
        shape-rendering: crispEdges;
    }

    .nvd3 .foreground path {
        fill: none;
        stroke-opacity: .7;
    }

    .nvd3 .nv-parallelCoordinates-brush .extent {
        fill: #fff;
        fill-opacity: .6;
        stroke: gray;
        shape-rendering: crispEdges;
    }

    .nvd3 .nv-parallelCoordinates .hover  {
        fill-opacity: 1;
        stroke-width: 3px;
    }


    .nvd3 .missingValuesline line {
      fill: none;
      stroke: black;
      stroke-width: 1;
      stroke-opacity: 1;
      stroke-dasharray: 5, 5;
    }

    .nvd3.nv-pie path {
        stroke-opacity: 0;
        transition: fill-opacity 250ms linear, stroke-width 250ms linear, stroke-opacity 250ms linear;
    }

    .nvd3.nv-pie .nv-pie-title {
        font-size: 24px;
        fill: rgba(19, 196, 249, 0.59);
    }

    .nvd3.nv-pie .nv-slice text {
        stroke: #000;
        stroke-width: 0;
    }

    .nvd3.nv-pie path {
        stroke: #fff;
        stroke-width: 1px;
        stroke-opacity: 1;
    }

    .nvd3.nv-pie path {
        fill-opacity: .7;
    }

    .nvd3.nv-pie .hover path {
        fill-opacity: 1;
    }

    .nvd3.nv-pie .nv-label {
        pointer-events: none;
    }

    .nvd3.nv-pie .nv-label rect {
        fill-opacity: 0;
        stroke-opacity: 0;
    }

    /* scatter */
    .nvd3 .nv-groups .nv-point.hover {
        stroke-width: 20px;
        stroke-opacity: .5;
    }

    .nvd3 .nv-scatter .nv-point.hover {
        fill-opacity: 1;
    }

    .nv-noninteractive {
        pointer-events: none;
    }

    .nv-distx, .nv-disty {
        pointer-events: none;
    }

    /* sparkline */
    .nvd3.nv-sparkline path {
        fill: none;
    }

    .nvd3.nv-sparklineplus g.nv-hoverValue {
        pointer-events: none;
    }

    .nvd3.nv-sparklineplus .nv-hoverValue line {
        stroke: #333;
        stroke-width: 1.5px;
    }

    .nvd3.nv-sparklineplus,
    .nvd3.nv-sparklineplus g {
        pointer-events: all;
    }

    .nvd3 .nv-hoverArea {
        fill-opacity: 0;
        stroke-opacity: 0;
    }

    .nvd3.nv-sparklineplus .nv-xValue,
    .nvd3.nv-sparklineplus .nv-yValue {
        stroke-width: 0;
        font-size: .9em;
        font-weight: normal;
    }

    .nvd3.nv-sparklineplus .nv-yValue {
        stroke: #f66;
    }

    .nvd3.nv-sparklineplus .nv-maxValue {
        stroke: #2ca02c;
        fill: #2ca02c;
    }

    .nvd3.nv-sparklineplus .nv-minValue {
        stroke: #d62728;
        fill: #d62728;
    }

    .nvd3.nv-sparklineplus .nv-currentValue {
        font-weight: bold;
        font-size: 1.1em;
    }
    /* stacked area */
    .nvd3.nv-stackedarea path.nv-area {
        fill-opacity: .7;
        stroke-opacity: 0;
        transition: fill-opacity 250ms linear, stroke-opacity 250ms linear;
    }

    .nvd3.nv-stackedarea path.nv-area.hover {
        fill-opacity: .9;
    }


    .nvd3.nv-stackedarea .nv-groups .nv-point {
        stroke-opacity: 0;
        fill-opacity: 0;
    }

    .nvtooltip {
        position: absolute;
        background-color: rgba(255,255,255,1.0);
        color: rgba(0,0,0,1.0);
        padding: 1px;
        border: 1px solid rgba(0,0,0,.2);
        z-index: 10000;
        display: block;

        font-family: Arial, sans-serif;
        font-size: 13px;
        text-align: left;
        pointer-events: none;

        white-space: nowrap;

        -webkit-user-select: none;

           -moz-user-select: none;

            -ms-user-select: none;

                user-select: none;
    }

    .nvtooltip {
        background: rgba(255,255,255, 0.8);
        border: 1px solid rgba(0,0,0,0.5);
        border-radius: 4px;
    }

    /*Give tooltips that old fade in transition by
        putting a "with-transitions" class on the container div.
    */
    .nvtooltip.with-transitions, .with-transitions .nvtooltip {
        transition: opacity 50ms linear;

        transition-delay: 200ms;
    }

    .nvtooltip.x-nvtooltip,
    .nvtooltip.y-nvtooltip {
        padding: 8px;
    }

    .nvtooltip h3 {
        margin: 0;
        padding: 4px 14px;
        line-height: 18px;
        font-weight: normal;
        background-color: rgba(247,247,247,0.75);
        color: rgba(0,0,0,1.0);
        text-align: center;

        border-bottom: 1px solid #ebebeb;

        border-radius: 5px 5px 0 0;
    }

    .nvtooltip p {
        margin: 0;
        padding: 5px 14px;
        text-align: center;
    }

    .nvtooltip span {
        display: inline-block;
        margin: 2px 0;
    }

    .nvtooltip table {
        margin: 6px;
        border-spacing:0;
    }


    .nvtooltip table td {
        padding: 2px 9px 2px 0;
        vertical-align: middle;
    }

    .nvtooltip table td.key {
        font-weight: normal;
    }

    .nvtooltip table td.key.total {
        font-weight: bold;
    }

    .nvtooltip table td.value {
        text-align: right;
        font-weight: bold;
    }

    .nvtooltip table td.percent {
        color: darkgray;
    }

    .nvtooltip table tr.highlight td {
        padding: 1px 9px 1px 0;
        border-bottom-style: solid;
        border-bottom-width: 1px;
        border-top-style: solid;
        border-top-width: 1px;
    }

    .nvtooltip table td.legend-color-guide div {
        width: 8px;
        height: 8px;
        vertical-align: middle;
    }

    .nvtooltip table td.legend-color-guide div {
        width: 12px;
        height: 12px;
        border: 1px solid #999;
    }

    .nvtooltip .footer {
        padding: 3px;
        text-align: center;
    }

    .nvtooltip-pending-removal {
        pointer-events: none;
        display: none;
    }


    /****
    Interactive Layer
    */
    .nvd3 .nv-interactiveGuideLine {
        pointer-events:none;
    }

    .nvd3 line.nv-guideline {
        stroke: #ccc;
    }
    `;

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
            ],
            triglycerides: [
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




