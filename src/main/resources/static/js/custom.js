
getYear();

// to get current year
function getYear() {

    const currentDate = new Date();
    const currentYear = currentDate.getFullYear();
    document.querySelector("#displayYear").innerHTML = currentYear;

    if(document.getElementById('rstartdate') && document.getElementById('renddate')){
        rstartdate = document.getElementById('rstartdate')
        renddate = document.getElementById('renddate')

        const today = currentDate.toISOString().split('T')[0];
        rstartdate.setAttribute('min', today);

        if(rstartdate.value){
            const nextDay = new Date(rstartdate.value);
            const tomorrow = nextDay.toISOString().split('T')[0];
            renddate.setAttribute('min', tomorrow);
        }

    }
}



// owl carousel 

$('.owl-carousel').owlCarousel({
    loop: true,
    margin: 10,
    nav: true,
    autoplay: true,
    autoplayHoverPause: true,
    responsive: {
        0: {
            items: 1
        },
        600: {
            items: 3
        },
        1000: {
            items: 6
        }
    }
})



