$(document).ready(function(){
    let pid = localStorage.getItem('PID');
    $.ajax({
      url : "../../s_journal/graph/server/getmeasurements.php?q="+pid.trim(),
      type : "GET",
      success : function(data){
  
        let time = [];              
        let sys = [];   
        let dia = [];    
        let hr = []; 
  
        for (let i in data) {
          let d = new Date(data[i].Time);
          time.push(d.toLocaleString());
          sys.push(data[i].Sys);
          dia.push(data[i].Dia);
          hr.push(data[i].HR); 
        }
  
        let chart    = document.getElementById('mycanvas').getContext('2d');
        let gSys     = chart.createLinearGradient(0, 0, 0, 450);
        let gDia     = chart.createLinearGradient(0, 0, 0, 450);
        let gHr      = chart.createLinearGradient(0, 0, 0, 450);
  
        gSys.addColorStop(0, 'rgba(255, 0, 0, 0.5)');
        gSys.addColorStop(0.5, 'rgba(255, 0, 0, 0.25)');
        gSys.addColorStop(1, 'rgba(255, 0, 0, 0)');
  
        gDia.addColorStop(0, 'rgba(255, 0, 255, 0.5)');
        gDia.addColorStop(0.5, 'rgba(255, 0, 255, 0.25)');
        gDia.addColorStop(1, 'rgba(255, 0, 255, 0)');
  
        gHr.addColorStop(0, 'rgba(0, 50, 255, 0.5)');
        gHr.addColorStop(0.5, 'rgba(0, 50, 255, 0.25)');
        gHr.addColorStop(1, 'rgba(0, 50, 255, 0)');
  
        let chartdata = {
          labels: time,
          datasets: [
            {
              label: "Systolic Pressure",
              fill: false,
              lineTension: 0.1,
              backgroundColor: gSys,
              borderColor: "rgba(255, 0, 0, 0.85)",
              pointBackgroundColor: "white",
              borderWidth: 2,
              pointHoverBackgroundColor: "rgba(155, 0, 0, 1)",
              pointHoverBorderColor: "rgba(155, 0, 0, 1)",
              data: sys
            },
            {
              label: "Dialostic Pressure",
              fill: false,
              lineTension: 0.1,
              backgroundColor: gDia,
              borderColor: "rgba(255, 0, 255, 0.85)",
              pointBackgroundColor: "white",
              borderWidth: 2,
              pointHoverBackgroundColor: "rgba(155, 0, 155, 1)",
              pointHoverBorderColor: "rgba(155, 0, 155, 1)",
              data: dia
            },
            {
              label: "Heartrate",
              fill: false,
              lineTension: 0.1,
              backgroundColor: gHr,
              pointBackgroundColor: "white",
              borderWidth: 2,
              borderColor: "rgba(0, 50, 255, 0.85)",
              pointHoverBackgroundColor: "rgba(0, 0, 155, 0.75)",
              pointHoverBorderColor: "rgba(0, 0, 155, 0.75)",
              data: hr
            }
          ]
        };
  
        let options = {
          responsive: true,
          maintainAspectRatio: true,
          animation: {
            easing: 'easeInOutQuad',
            duration: 520
          },
          scales: {
            xAxes: [{
              gridLines: {
                color: 'rgba(200, 200, 200, 0.05)',
                lineWidth: 1
              }
            }],
            yAxes: [{
              gridLines: {
                color: 'rgba(200, 200, 200, 0.08)',
                lineWidth: 1
              }
            }]
          },
          elements: {
            line: {
              tension: 0.4
            }
          },
          legend: {
            display: true,
          },
          point: {
            backgroundColor: 'white'
          },
          tooltips: {
            titleFontFamily: 'Open Sans',
            backgroundColor: 'rgba(0,0,0,0.5)',
            titleFontColor: 'white',
            caretSize: 5,
            cornerRadius: 2,
            xPadding: 10,
            yPadding: 10,
          }
        };
  
        Chart.defaults.global.defaultFontColor = "rgba(205,205,205,1)";
       
        let ctx = $("#mycanvas");
  
        let LineGraph = new Chart(ctx, {
          type: 'line',
          data: chartdata,
          options: options
        });
      },
      error : function(data) {
  
      }
    });
  });
  