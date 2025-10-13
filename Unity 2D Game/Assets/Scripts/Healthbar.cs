using UnityEngine;
using UnityEngine.UI;

public class Healthbar : MonoBehaviour
{ 
    public Slider slider;
    public Color low;
    public Color high;
    public Vector3 Offset;

    
    public void SetHealth(float health, float maxHealth)
    {
        slider.gameObject.SetActive(health < maxHealth);
        slider.value = health;
        slider.maxValue = maxHealth;

        slider.fillRect.GetComponentInChildren<Image>().color = Color.Lerp(low,high, slider.normalizedValue);
    }

    void Update()
    {

        slider.transition.position = Camera.main.WorldToScreenPoint(transform.parent.position + Offset); //nastavi

    }
}
