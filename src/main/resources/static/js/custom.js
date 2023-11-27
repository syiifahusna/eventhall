function pickDate() {

    if(document.getElementById('floatingDateStart') && document.getElementById('floatingDateEnd')){
        rstartdate = document.getElementById('floatingDateStart')
        renddate = document.getElementById('floatingDateEnd')

        const currentDate = new Date();
        const today = currentDate.toISOString().split('T')[0];
        rstartdate.setAttribute('min', today);

        if(rstartdate.value){
            const nextDay = new Date(rstartdate.value);
            const tomorrow = nextDay.toISOString().split('T')[0];
            renddate.setAttribute('min', tomorrow);
        }

    }
}